package com.example.pokedexappcompose.data.use_case.pokemon_detail

import android.net.http.HttpException
import com.example.pokedexappcompose.common.Response
import com.example.pokedexappcompose.data.repository.PokemonRepositoryImpl
import com.example.pokedexappcompose.data.response.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class PokemonDetailUseCase @Inject constructor(private val pokemonRepository: PokemonRepositoryImpl) {

    operator fun invoke(pokemonName:String) : Flow<Response<Pokemon>> = flow{
        try {
            emit(Response.Loading())
            val pokemon=pokemonRepository.getPokemonInfo(pokemonName = pokemonName)
            emit(Response.Success(pokemon))
        }catch (e:Exception){
            emit(Response.Error(message = "An unknown error occured."))
        }catch (e: HttpException){
            emit(Response.Error(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e: IOException){
            emit(Response.Error("Couldn't reach server.Check your internet connection.."))
        }
    }
}