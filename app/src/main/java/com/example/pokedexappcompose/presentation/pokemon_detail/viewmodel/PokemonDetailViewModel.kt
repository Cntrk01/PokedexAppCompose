package com.example.pokedexappcompose.presentation.pokemon_detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexappcompose.common.Constants
import com.example.pokedexappcompose.common.Response
import com.example.pokedexappcompose.data.use_case.pokemon_detail.PokemonDetailUseCase
import com.example.pokedexappcompose.presentation.pokemon_detail.state.PokemonDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val pokemonDetailUseCase: PokemonDetailUseCase,
    private val savedStateHandle: SavedStateHandle) :
    ViewModel() {

    private val _state = MutableStateFlow(PokemonDetailState())
    val state : StateFlow<PokemonDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.POKEMON_NAME)?.let{
            getPokemonDetail(it.lowercase(Locale.ROOT))
        }
    }

    private fun getPokemonDetail(pokemonName: String) = viewModelScope.launch {
        pokemonDetailUseCase.invoke(pokemonName = pokemonName)
            .collectLatest { response ->
                when (response) {
                    is Response.Loading->{
                        _state.update {detailState->
                            detailState.copy(
                                loading = true
                            )
                        }
                    }

                    is Response.Error->{
                        _state.update {detailState->
                            detailState.copy(
                                error = response.message.toString(),
                                loading = false
                            )
                        }
                    }

                    else->{
                        _state.update {detailState->
                            detailState.copy(
                                error = "",
                                loading = false,
                                pokemonDetail = response.data
                            )
                        }
                        println(response.data)
                    }
                }
            }
    }
}