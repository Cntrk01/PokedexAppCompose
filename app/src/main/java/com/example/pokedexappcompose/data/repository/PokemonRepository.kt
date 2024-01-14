package com.example.pokedexappcompose.data.repository

import com.example.pokedexappcompose.data.response.Pokemon
import com.example.pokedexappcompose.data.response.PokemonList

interface PokemonRepository {
    suspend fun getPokemonList(limit:Int,offset:Int) : PokemonList
    suspend fun getPokemonInfo(pokemonName:String) : Pokemon
}