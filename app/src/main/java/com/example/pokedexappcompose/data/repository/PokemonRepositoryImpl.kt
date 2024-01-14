package com.example.pokedexappcompose.data.repository

import com.example.pokedexappcompose.data.remote.PokemonApi
import com.example.pokedexappcompose.data.response.Pokemon
import com.example.pokedexappcompose.data.response.PokemonList
import javax.inject.Inject

class PokemonRepositoryImpl
    @Inject constructor(private val pokemonApi: PokemonApi)
    : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonList {
        return pokemonApi.getPokemonList(limit=limit,offset=offset)
    }

    override suspend fun getPokemonInfo(pokemonName: String): Pokemon {
        return pokemonApi.getPokemonInfo(name = pokemonName)
    }
}