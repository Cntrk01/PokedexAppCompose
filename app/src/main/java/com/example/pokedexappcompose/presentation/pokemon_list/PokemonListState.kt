package com.example.pokedexappcompose.presentation.pokemon_list

import com.example.pokedexappcompose.data.PokemonListEntry

data class PokemonListState(
    var pokemonList : List<PokemonListEntry> = emptyList(),
    var error : String ="",
    var isLoading : Boolean =false,
    var endReached : Boolean = false
)
