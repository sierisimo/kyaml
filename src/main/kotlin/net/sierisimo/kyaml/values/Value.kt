package net.sierisimo.kyaml.values

open class Value(val value: Any?) {
    open fun toInt() = (value as String).toInt()
    open fun toBoolean() = (value as String).toBoolean()

    override fun toString(): String {
        val strValue = value.toString()
        val hasDoubleQuotes = strValue.startsWith("\"") && strValue.endsWith("\"")
        val hasSingleQuotes = strValue.startsWith("'") && strValue.endsWith("'")

        if (hasDoubleQuotes or hasSingleQuotes) {
            return strValue.substring(1 until (strValue.length - 1))
        }

        return strValue
    }
}