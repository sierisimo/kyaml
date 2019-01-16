package net.sierisimo.kyaml.validators

class IsOnlyDigitsValidator : Validator {
    override fun validate(content: String): Boolean = content.isOnlyDigits()

    private fun String.isOnlyDigits(): Boolean = "[0-9]+".toRegex().matches(this)
}