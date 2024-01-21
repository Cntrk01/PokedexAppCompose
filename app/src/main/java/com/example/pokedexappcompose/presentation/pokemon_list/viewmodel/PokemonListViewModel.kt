package com.example.pokedexappcompose.presentation.pokemon_list.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedexappcompose.common.Constants.PAGE_SIZE
import com.example.pokedexappcompose.data.use_case.pokemon_list.PokemonListUseCase
import com.example.pokedexappcompose.presentation.pokemon_list.state.PokemonListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.pokedexappcompose.common.Response
import com.example.pokedexappcompose.data.PokemonListEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val pokemonListUseCase: PokemonListUseCase) :
    ViewModel() {

    private var currentPage = 0
    private val _state = MutableStateFlow(PokemonListState())
    val state: StateFlow<PokemonListState> = _state

    private val pokemonList = mutableStateListOf<PokemonListEntry>()

    init {
        loadPokemonPaging()
    }

    fun searchPokemonList(query: String) = viewModelScope.launch {
        _state.value.apply {
            val listToSearch = if (isSearchStarting) {
                pokemonList
            } else {
                cachedPokemonList
            }

            if (query.isEmpty()) {
                _state.update { pokemonState ->
                    pokemonState.copy(
                        pokemonList = cachedPokemonList,
                        isSearching = false,
                        isSearchStarting = true,
                        isLoading=false
                    )
                }
                return@launch
            }

            val result = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }
            if (isSearchStarting) {
               _state.update {
                   it.copy(
                       cachedPokemonList = pokemonList,
                       isSearchStarting = false,
                       isLoading=false
                   )
               }
            }
            if (result.isEmpty()){
                _state.update {
                    it.copy(
                        pokemonList = emptyList(),
                        isSearching = false,
                        isLoading=false,
                        error = "Pokemon Is Not Found..."
                    )
                }
            }else{
                _state.update {
                    it.copy(
                        pokemonList = result,
                        isSearching = true,
                        isLoading=false,
                        error = ""
                    )
                }
            }
        }
    }

    fun loadPokemonPaging() = viewModelScope.launch {
        pokemonListUseCase.invoke(limit = PAGE_SIZE, offset = currentPage * PAGE_SIZE)
            .collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                error = ""
                            )
                        }
                    }

                    is Response.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = response.message.toString()
                            )
                        }
                    }
                    else -> {
                        _state.value.endReached =
                            currentPage * PAGE_SIZE >= response.data!!.count

                        val pokemonEntries = response.data.results.map { result ->
                            //Burada apide sonu / ile bittiği için endWith ile kontrol edip siliyoruz.Ve son karakteri tekrar alıp digit mi diye kontrol ediyoruz.!
                            //Böylelikle int değer numara alacağız.
                            val number = if (result.url.endsWith("/")) {
                                result.url.dropLast(1).takeLastWhile { it.isDigit() }
                            } else {
                                result.url.takeLastWhile { it.isDigit() }
                            }
                            val url =
                                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                            //capitalize ile ilk harfini büyük yapıyoruz.
                            PokemonListEntry(
                                pokemonName = result.name.capitalize(Locale.ROOT),
                                imageUrl = url,
                                number = number.toInt()
                            )
                        }
                        currentPage++
                        pokemonList += pokemonEntries
                        _state.update {
                            it.copy(
                                pokemonList = pokemonList,
                                isLoading = false,
                                error = ""
                            )
                        }
                    }
                }
            }
    }

    //Alınan drawable nesnesindeki rengi analiz ederek hesaplayarak bize renk çıkarıyor.
    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(color = colorValue))
            }
        }
    }
}