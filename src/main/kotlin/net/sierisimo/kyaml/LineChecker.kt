package net.sierisimo.kyaml

interface LineChecker {
    fun String.isComment() = startsWith("#")

    fun String.isStart(): Boolean {
        val startRegex = """---((( +)(false|~|\.{3}|\s+\n|\s*#.*))|\n|\s*)"""
                .toRegex()

        return matches(startRegex)
    }

    val String.isNotImportantLine: Boolean
        get() = isComment() or isBlank() or isStart()
}