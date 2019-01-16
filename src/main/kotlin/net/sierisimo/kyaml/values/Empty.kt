package net.sierisimo.kyaml.values

import net.sierisimo.kyaml.KYAMLException

object Empty : Value(Unit) {
    override fun toInt(): Nothing = throw KYAMLException("Empty value cannot be converted to int")
    override fun toBoolean(): Nothing = throw KYAMLException("Empty value cannot be converted to boolean")
    override fun toString(): Nothing = throw KYAMLException("Empty value has no string representation")
}