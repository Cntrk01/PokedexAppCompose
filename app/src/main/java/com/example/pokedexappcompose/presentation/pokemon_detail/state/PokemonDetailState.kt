package com.example.pokedexappcompose.presentation.pokemon_detail.state

import com.example.pokedexappcompose.data.response.Pokemon

data class PokemonDetailState (
    val loading : Boolean =false,
    val error : String ="",
    val pokemonDetail : Pokemon ?=null
)