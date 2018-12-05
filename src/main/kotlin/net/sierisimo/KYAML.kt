package net.sierisimo

class KYAML {
    var keys = mutableSetOf<String>()
        private set
    var values = mutableListOf<Any>()
        private set

    companion object {
        private const val regexString = """---((( {1})(false|~|\.{3}|\s+\n|\s*#.*))|\n)"""

        @JvmStatic
        fun of(content: String): KYAML {
            if (content.isBlank()) throw KYAMLException("Initialization content cannot be blank")

            val possibleStarts = content
                    .split("\n")
                    .filter { it.matches(regexString.toRegex()) }

            if (possibleStarts.isEmpty()) throw KYAMLException("Valid start not found in YAML")

            return KYAML()
        }
    }
}

class KYAMLException(message: String) : RuntimeException(message)
