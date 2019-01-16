package net.sierisimo.kyaml

import net.sierisimo.kyaml.values.Empty
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

@DisplayName("Main set of tests")
internal class KYAMLTest {
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

    @Test
    fun `comments only YAML is NOT a valid YAML`() {
        assertFailsWith(KYAMLException::class) {
            val content = "#"
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class) {
            val content = """#
                |#
                |#
            """.trimMargin()
            KYAML.of(content)
        }
    }

    @Test
    fun `Key and Value should exist with small YAML, it can be an int`() {
        val content = """---
            |foo: 1
        """.trimMargin()
        val result = KYAML.of(content)
        val output = result["foo"]?.toInt()

        assertEquals(1, output)
    }

    @Test
    fun `Key and Value should exist with small YAML, it can be a string`() {
        var content = """---
            |foo: "1"
        """.trimMargin()
        var result = KYAML.of(content)
        var output = result["foo"].toString()

        assertEquals("1", output)

        content = """---
            |foo: '1'
        """.trimMargin()
        result = KYAML.of(content)
        output = result["foo"].toString()

        assertEquals("1", output)
    }

    @Test
    fun `Key and Value should exist with small YAML, it can be a boolean`() {
        var content = """---
            |foo: true
        """.trimMargin()
        var result = KYAML.of(content)
        assertEquals(true, result["foo"]?.toBoolean())

        content = """---
            |foo: false
        """.trimMargin()
        result = KYAML.of(content)
        assertEquals(false, result["foo"]?.toBoolean())
    }

    @Test
    fun `Key and Value should exist with small YAML, it can be a null`() {
        val content = """---
            |foo: null
        """.trimMargin()
        val result = KYAML.of(content)

        assertNull(result["foo"])
    }

    @Test
    fun `Empty value should return 'Empty' object`() {
        val content = """---
            |foo
        """.trimMargin()
        val result = KYAML.of(content)

        assertTrue(result["foo"] is Empty)
    }
}