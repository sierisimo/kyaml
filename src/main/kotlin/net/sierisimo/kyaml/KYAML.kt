package net.sierisimo.kyaml

import net.sierisimo.kyaml.validators.HasStartValidator
import net.sierisimo.kyaml.validators.IsOnlyDigitsValidator
import net.sierisimo.kyaml.validators.IsQuotedValidator
import net.sierisimo.kyaml.values.Empty
import net.sierisimo.kyaml.values.Value

class KYAML(content: String) {
    private val rawMap = mutableMapOf<String, Pair<KYAMLType, String?>?>()

    private val lineChecker = object : LineChecker {}

    init {
        val linesAfterStart = getLinesAfterStart(content)
        val partsList = linesAfterStart.map {
            it.split(":")
        }.filter {
            it.isNotEmpty()
        }

        partsList.forEach { stringList ->
            val key = stringList[0].trim()

            rawMap[key] =
                    if (stringList.size > 1) {
                        val value = stringList[1].trim()

                        getWithType(value)
                    } else Pair(KYAMLType.EMPTY, null)
        }
    }

    private fun getLinesAfterStart(content: String): List<String> {
        return content.split("\n")
                .map(String::trim)
                .dropWhile { with(lineChecker) { it.isNotImportantLine } }
    }

    private fun getWithType(value: String): Pair<KYAMLType, String?> {
        val isQuotedValidator = IsQuotedValidator()
        val isOnlyDigitsValidator = IsOnlyDigitsValidator()

        return when {
            !isQuotedValidator.validate(value) and (value == "null") -> Pair(KYAMLType.NULL, null)
            isOnlyDigitsValidator.validate(value) -> Pair(KYAMLType.INT, value)
            value == "true" || value == "false" -> Pair(KYAMLType.BOOLEAN, value)
            else -> Pair(KYAMLType.STRING, value)
        }
    }

    operator fun get(key: String): Value? {
        require(key.isNotBlank()) { "Key cannot be empty or null" }

        val valueInMap = rawMap[key]

        return when (valueInMap?.first) {
            KYAMLType.EMPTY -> Empty
            KYAMLType.NULL, null -> null
            else -> Value(valueInMap.second)
        }
    }

    companion object {
        /**
         * In this method the content is validated, the moment the [KYAML] is created
         * the content is a valid YAML and can be parsed in the class
         */
        @JvmStatic
        fun of(content: String): KYAML {
            require(content.isNotBlank()) { "Initialization content cannot be blank" }

            val startExistsAndItsValid = HasStartValidator().validate(content)
            if (!startExistsAndItsValid) throw KYAMLException("Valid start not found in YAML Document")

            return KYAML(content)
        }
    }
}

