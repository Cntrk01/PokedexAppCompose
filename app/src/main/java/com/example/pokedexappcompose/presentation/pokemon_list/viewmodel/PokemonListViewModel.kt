package com.example.pokedexappcompose.presentation.pokemon_list.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import com.example.pokedexappcompose.data.use_case.pokemon_list.PokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val pokemonListUseCase: PokemonListUseCase)
    : ViewModel(){

    //Alınan drawable nesnesindeki rengi analiz ederek hesaplayarak bize renk çıkarıyor.
    fun calcDominantColor(drawable:Drawable,onFinish:(Color)->Unit){
        val bmp=(drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888,true)

        Palette.from(bmp).generate {palette->
            palette?.dominantSwatch?.rgb?.let {colorValue->
                onFinish(Color(color = colorValue))
            }
        }
    }
}