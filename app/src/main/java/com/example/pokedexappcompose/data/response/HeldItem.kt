package com.example.pokedexappcompose.data.response

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)