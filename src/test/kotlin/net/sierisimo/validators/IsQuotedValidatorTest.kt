package net.sierisimo.validators

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DisplayName("Checks for quotes validation")
internal class IsQuotedValidatorTest {
    private val validator = IsQuotedValidator()

    @ParameterizedTest
    @ValueSource(strings = ["'foo'",
        """'bar and "someone" '""",
        "''",

        """'multiline
        |content
        |is still
        |valid
        |content'
    """])
    fun `check strings starting and ending with single quote can have different values`(content: String) {
        val result = validator.validate(content.trimMargin())
        assertTrue(result)
    }

    @ParameterizedTest
    @ValueSource(strings = [ "Text", "Foo'", "'", "  ",
        """'
        |Foo
        |Bar
    """,
        "'Text' After"])
    fun `not matched single quotes at beginning and end are not valid`(content: String) {
        val result = validator.validate(content.trimMargin())
        assertFalse(result)
    }
}