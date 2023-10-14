package com.ichsnn.core

import com.ichsnn.core.utils.Format
import org.junit.Assert
import org.junit.Test

class FormatterTest {
    @Test
    fun `format pokemon id to string`() {
        Assert.assertEquals("#0001", Format.pokemonIdToString(1))
        Assert.assertEquals("#0010", Format.pokemonIdToString(10))
        Assert.assertEquals("#0100", Format.pokemonIdToString(100))
        Assert.assertEquals("#1000", Format.pokemonIdToString(1000))
    }

    @Test
    fun `sentence capital the pokemon name`() {
        Assert.assertEquals("Pikachu", Format.sentenceCapital("pikachu"))
    }
}