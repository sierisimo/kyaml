package net.sierisimo

import net.sierisimo.validators.HasStartValidator
import net.sierisimo.validators.IsQuotedValidator

class KYAML(content: String) {
    private val rawMap = mutableMapOf<String, Pair<KYAMLType, String?>?>()

    private val lineChecker = object : LineChecker {}

    init {
        val isQuotedValidator = IsQuotedValidator()

        val afterStartLines = content.split("\n")
                .map(String::trim)
                .dropWhile { with(lineChecker) { it.isNotImportantLine } }
        val partsList = afterStartLines.map { it.split(":") }.filter { it.isNotEmpty() }

        partsList.forEach { stringList ->
            val key = stringList[0].trim()

            rawMap[key] =
                    if (stringList.size > 1) {
                        val value = stringList[1].trim()

                        if (!isQuotedValidator.validate(value) and (value == "null")) Pair(KYAMLType.NULL, null)
                        else Pair(KYAMLType.STRING, stringList.drop(1).joinToString("").trim())
                    } else Pair(KYAMLType.EMPTY, null)
        }
    }


    operator fun get(key: String): Value? {
        require(key.isNotBlank()) { "Key cannot be empty or null" }

        val valueInMap = rawMap[key]

        return when (valueInMap?.first) {
            KYAMLType.EMPTY -> Empty
            KYAMLType.NULL, null -> null
            else -> Value(valueInMap)
        }
    }

    companion object {
        /**
         * In this method the content is validated, the moment the [KYAML] is created
         * the content is a valid YAML and can be parsed in the class
         */
        @JvmStatic
        fun of(content: String): KYAML {
            if (content.isBlank()) throw KYAMLException("Initialization content cannot be blank")

            val startExistsAndItsValid = HasStartValidator().validate(content)

            if (!startExistsAndItsValid) throw KYAMLException("Valid start not found in YAML Document")

            return KYAML(content)
        }
    }

    enum class KYAMLType {
        STRING, BOOLEAN, NULL, EMPTY,
    }
}

open class Value(val value: Any?) {
    open fun toInt() = value as? Int
    open fun toBoolean() = value as? Boolean
    override fun toString(): String = value.toString()
}

object Empty : Value(Unit) {
    override fun toInt(): Nothing = throw KYAMLException("Empty value cannot be converted to int")
    override fun toBoolean(): Nothing = throw KYAMLException("Empty value cannot be converted to boolean")
    override fun toString(): Nothing = throw KYAMLException("Empty value has no string representation")
}