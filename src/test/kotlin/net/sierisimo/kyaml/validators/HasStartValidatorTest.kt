package net.sierisimo.kyaml.validators

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DisplayName("Rules for Start of YAML test")
internal class HasStartValidatorTest {
    val validator = HasStartValidator()

    @ParameterizedTest
    @ValueSource(strings = ["", " ",
        """

        """,
        "     "])
    fun `validator should return false when empty string`(content: String) {
        val result = validator.validate(content)

        assertFalse(result)
    }

    @ParameterizedTest
    @ValueSource(strings = ["""#
        |--- """,

        """#
        |#
        |--- """,

        "--- ",

        """#
        |#
        |#
        |#
        |#
        |#
        |#
        |---
        |#
        |#"""])
    fun `YAML can have any number of comments before the actual start`(content: String) {
        assertTrue(validator.validate(content.trimMargin()))
    }

    @ParameterizedTest
    @ValueSource(strings = ["""Foo
            |---
        """,
        """1
            |---
        """,
        """...
            |---
        """
    ])
    fun `Other elements apart from comments are not allowed`(content:   String) {
        assertFalse(validator.validate(content.trimMargin()))
    }

    @ParameterizedTest
    @ValueSource(strings = ["--- false", "--- ...", "--- ~", "---                   ~", "---                    false",
        """---
        |---
        |--- ~
    """])
    fun `Valid starts for empty document are allowed`(content: String) {
        assertTrue(validator.validate(content.trimMargin()))
    }

    @ParameterizedTest
    @ValueSource(strings = ["--- #", "---             #", "---     #0123456789---...,,,,,", "--- #  #"])
    fun `Comments are valid after start`(content: String) {
        assertTrue(validator.validate(content.trimMargin()))
    }

    @ParameterizedTest
    @ValueSource(strings = ["--- $", """--- """", """--- 1""", "--- '", "--- |"])
    fun `No invalid elements after start in same line`(content: String) {
        assertFalse(validator.validate(content.trimMargin()))
    }

    @ParameterizedTest
    @ValueSource(strings = ["-", "--", " ---", "#---", "---~"])
    fun `Bad start is NOT a valid start`(content: String) {
        assertFalse(validator.validate(content.trimMargin()))
    }

    @ParameterizedTest
    @ValueSource(strings = ["""---
        |Foo: Bar
    """,
        """---
        |# A comment
    """,
        """---
        |...
    """])
    fun `Content after start shouldn't be a problem`(content: String) {
        assertTrue(validator.validate(content.trimMargin()))
    }
}