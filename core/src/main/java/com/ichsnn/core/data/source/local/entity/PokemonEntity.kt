package com.ichsnn.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    @ColumnInfo(name = "pokemonId")
    var pokemonId: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "image_url")
    var imageUrl: String,

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)