package com.example.pokedexappcompose.presentation.pokemon_detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokedexappcompose.presentation.pokemon_detail.viewmodel.PokemonDetailViewModel

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    backItemClick : ()->Unit
) {
    val state by viewModel.state.collectAsState()

    PokemonDetailTopSection(backItemClick = backItemClick)

    if (state.pokemonDetail?.name?.isNotBlank() == true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(dominantColor)
                .padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {

                AsyncImage(
                    modifier = Modifier
                        .size(pokemonImageSize)
                        .offset(y = topPadding),
                    model = state.pokemonDetail?.sprites?.frontDefault,
                    contentDescription = "Pokemon Image",
                )
            }
        }
    }

    if (state.loading) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }

    if (state.error.isNotBlank()) {
        Text(
            text = state.error,
            color = Color.Red,
        )
    }
}

@Composable
private fun PokemonDetailTopSection(
    backItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .background(
                Brush.verticalGradient(
                    listOf(Color.Black, Color.Transparent)
                )
            ), contentAlignment = Alignment.TopCenter
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                   backItemClick.invoke()
                })
    }
}