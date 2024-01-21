package com.example.pokedexappcompose.presentation.pokemon_list.state

import com.example.pokedexappcompose.data.PokemonListEntry

data class PokemonListState(
    var pokemonList : List<PokemonListEntry> = emptyList(),
    var error : String ="",
    var isLoading : Boolean =false,
    var endReached : Boolean = false,
    var isSearching : Boolean =false,
    var isSearchStarting : Boolean =true,
    var cachedPokemonList : List<PokemonListEntry> = emptyList(),
    var lastQuery : String=""
)
