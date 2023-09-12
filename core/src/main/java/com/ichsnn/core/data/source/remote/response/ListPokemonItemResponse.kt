package com.ichsnn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListPokemonItemResponse(
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("url")
    val url: String
)