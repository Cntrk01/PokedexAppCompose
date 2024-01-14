package com.example.pokedexappcompose.di

import com.example.pokedexappcompose.common.Constants.BASE_URL
import com.example.pokedexappcompose.data.remote.PokemonApi
import com.example.pokedexappcompose.data.repository.PokemonRepositoryImpl
import com.example.pokedexappcompose.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePokemonApi() : PokemonApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }

    @Singleton
    @Provides
    fun providePokemonRepository(pokeApi:PokemonApi) : PokemonRepository {
        return PokemonRepositoryImpl(pokemonApi = pokeApi)
    }
}