package net.sierisimo.kyaml

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertFailsWith

@DisplayName("Invalid set of tests ")
internal class InvalidYAMLTest {
    @Test
    fun `empty YAML is NOT a valid YAML`() {
        assertFailsWith(IllegalArgumentException::class) {
            val content = ""
            KYAML.of(content)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["""foo""", """foo: |""", """? |""", """..."""])
    fun `no explicit start is NOT a valid YAML`(content: String) {
        assertFailsWith(KYAMLException::class) {
            KYAML.of(content)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["#",
        """#
        |#
        |#
        """])
    fun `comments only YAML is NOT a valid YAML`(content: String) {
        assertFailsWith(KYAMLException::class) {
            KYAML.of(content.trimMargin())
        }
    }
}