package net.sierisimo.kyaml.validators

interface Validator {
    fun validate(content: String): Boolean
}
