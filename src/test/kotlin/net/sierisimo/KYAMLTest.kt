package net.sierisimo

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@DisplayName("Main set of tests")
class KYAMLTest {
    @Test
    fun `empty YAML is NOT a valid YAML`() {
        assertFailsWith(KYAMLException::class) {
            val content = ""
            KYAML.of(content)
        }
    }

    @Test
    fun `bad start is NOT a valid YAML`() {
        assertFailsWith(KYAMLException::class) {
            val content = """-"""
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class) {
            val content = """--"""
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class) {
            val content = """ ---"""
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class) {
            val content = """#---"""
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class) {
            val content = """---~"""
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
    fun `no invalid elements after start in same line`() {
        assertFailsWith(KYAMLException::class) {
            val content = """--- $"""
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class) {
            val content = """--- """"
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class) {
            val content = """--- 1"""
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class) {
            val content = """--- |"""
            KYAML.of(content)
        }
    }

    @Test
    fun `comments are valid after start`() {
        var content = """--- #"""
        var result = KYAML.of(content)

        with(result) {
            assertEquals(keys.size, emptySet<String>().size)
            assertEquals(values.size, emptyList<String>().size)
        }

        content = """---             #"""
        result = KYAML.of(content)

        with(result) {
            assertEquals(keys.size, emptySet<String>().size)
            assertEquals(values.size, emptyList<String>().size)
        }

        content = """---     #0123456789---...,,,,,"""
        result = KYAML.of(content)

        with(result) {
            assertEquals(keys.size, emptySet<String>().size)
            assertEquals(values.size, emptyList<String>().size)
        }
    }

    @Test
    fun `check an string can be passed with only --- ~`() {
        val content = """--- ~"""
        val result = KYAML.of(content)

        with(result) {
            assertEquals(keys.size, emptySet<String>().size)
            assertEquals(values.size, emptyList<String>().size)
        }
    }

    @Test
    fun `validate --- false is valid YAML`() {
        val content = """--- false"""
        val result = KYAML.of(content)

        with(result) {
            assertEquals(keys.size, emptySet<String>().size)
            assertEquals(values.size, emptyList<String>().size)
        }
    }

    @Test
    fun `validate --- … is valid YAML`() {
        val content = """--- ..."""
        val result = KYAML.of(content)

        with(result) {
            assertEquals(keys.size, emptySet<String>().size)
            assertEquals(values.size, emptyList<String>().size)
        }
    }

    @Test
    fun `comments only YAML is NOT a valid YAML`() {
        assertFailsWith(KYAMLException::class) {
            val content = "#"
            KYAML.of(content)
        }

        assertFailsWith(KYAMLException::class){
            val content = """#
                |#
                |#
            """.trimMargin()
            KYAML.of(content)
        }
    }


}