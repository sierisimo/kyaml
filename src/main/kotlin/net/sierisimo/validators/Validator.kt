package net.sierisimo.validators

interface Validator {
    fun validate(content: String): Boolean
}
