package com.example.pokedexappcompose.data.use_case.pokemon_list

import android.net.http.HttpException
import com.example.pokedexappcompose.data.response.PokemonList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.pokedexappcompose.common.Response
import com.example.pokedexappcompose.data.repository.PokemonRepository
import java.io.IOException
import javax.inject.Inject

class PokemonListUseCase @Inject constructor(private val pokemonRepository: PokemonRepository){

    operator fun invoke(limit:Int,offset:Int) : Flow<Response<PokemonList>> = flow{
        try {
            emit(Response.Loading())
            val pokemonList=pokemonRepository.getPokemonList(limit = limit, offset = offset)
            emit(Response.Success(pokemonList))
        }catch (e:Exception){
            emit(Response.Error(message = "An unknown error occured."))
        }catch (e: HttpException){
            emit(Response.Error(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e: IOException){
            emit(Response.Error("Couldn't reach server.Check your internet connection.."))
        }
    }
}