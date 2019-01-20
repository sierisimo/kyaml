package net.sierisimo.kyaml.validators

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse

internal class IsOnlyDigitsValidatorTest {
    private val isOnlyDigitsValidator = IsOnlyDigitsValidator()

    @Test
    fun `empty string should return false`() {
        assertFalse(isOnlyDigitsValidator.validate(""))
    }
}