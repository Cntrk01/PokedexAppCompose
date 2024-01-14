package com.example.pokedexappcompose.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld,
    @SerializedName("home")
    val home: Home,
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork,
    @SerializedName("showdown")
    val showdown: Showdown
)