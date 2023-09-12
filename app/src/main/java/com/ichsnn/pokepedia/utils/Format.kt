package com.ichsnn.pokepedia.utils

object Format {
    fun pokemonIdToString(pokemonId: Int): String = String.format("#%04d", pokemonId)
    fun sentenceCapital(string: String): String =
        string.split(' ').joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}