package net.sierisimo.kyaml.validators

class IsQuotedValidator : Validator {
    override fun validate(content: String): Boolean {
        if (content.isBlank()) return false
        if (content.length <= 1) return false

        return content.isDoubleQuoted() or content.isSingleQuoted()
    }

    private fun String.isDoubleQuoted() = startsWith("\"") and endsWith("\"")

    private fun String.isSingleQuoted() = startsWith("'") and endsWith("'")
}