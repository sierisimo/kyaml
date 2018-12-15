package net.sierisimo.validators

import net.sierisimo.LineChecker

class HasStartValidator : Validator, LineChecker {
    override fun validate(content: String): Boolean =
            content.lines()
                    .takeWhile { it.isNotImportantLine }
                    .count { it.isStart() } >= 1

}
