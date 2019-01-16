package net.sierisimo.kyaml.validators

import net.sierisimo.kyaml.LineChecker

class HasStartValidator : Validator, LineChecker {
    override fun validate(content: String): Boolean =
            content.lines()
                    .takeWhile { it.isNotImportantLine }
                    .count { it.isStart() } >= 1

}
