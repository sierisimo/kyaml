package net.sierisimo.kyaml.validators

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertFalse

internal class IsOnlyDigitsValidatorTest {
    private val isOnlyDigitsValidator = IsOnlyDigitsValidator()

    @Test
    fun `empty string should return false`() {
        assertFalse(isOnlyDigitsValidator.validate(""))
    }

    @ParameterizedTest
    @ValueSource(strings = [".", "1.0", "-1"])
    fun `special characters doesn't count as numbers`(string: String) {
        assertFalse(isOnlyDigitsValidator.validate(string))
    }
}