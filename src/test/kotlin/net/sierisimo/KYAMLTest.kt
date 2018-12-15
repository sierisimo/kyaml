package net.sierisimo

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

@DisplayName("Main set of tests")
internal class KYAMLTest {
    @Test
    fun `empty YAML is NOT a valid YAML`() {
        assertFailsWith(KYAMLException::class) {
            val content = ""
            KYAML.of(content)
        }
    }

    @Test
    fun `no explicit start is NOT a valid YAML`() {
        assertFailsWith(KYAMLException::class) {
            val content = """foo"""
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class) {
            val content = """foo: |"""
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class) {
            val content = """? |"""
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class) {
            val content = """..."""
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

        assertEquals(1, result["foo"]?.toInt())
    }

    @Test
    fun `Key and Value should exist with small YAML, it can be a string`() {
        val content = """---
            |foo: 1
        """.trimMargin()
        val result = KYAML.of(content)

        assertEquals("1", result["foo"].toString())
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