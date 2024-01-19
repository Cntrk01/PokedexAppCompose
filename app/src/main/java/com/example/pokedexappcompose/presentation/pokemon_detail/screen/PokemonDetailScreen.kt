package com.example.pokedexappcompose.presentation.pokemon_detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedexappcompose.presentation.pokemon_detail.state.PokemonDetailState
import com.example.pokedexappcompose.presentation.pokemon_detail.viewmodel.PokemonDetailViewModel
import kotlinx.coroutines.delay

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 250.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    backItemClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var loading by remember { mutableStateOf(state.loading) }

    LaunchedEffect(loading) {
        if (loading) {
            delay(2000)
            loading = false
        }
    }

    Column(
        modifier = Modifier
            .background(dominantColor)
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(dominantColor)
                .padding(bottom = 16.dp)
        ) {
            PokemonDetailTopSection(backItemClick = backItemClick)

            if (state.pokemonDetail?.name?.isNotBlank() == true) {
                PokemonDetailData(
                    state = state,
                    loading = loading,
                    pokemonImageSize = pokemonImageSize,
                    topPadding = topPadding
                )
            }
            if (loading) {
                LoadingProgressBar()
            }

            if (state.error.isNotBlank()) {
                ErrorMessage(state = state.error)
            }
        }
    }
}

@Composable
private fun PokemonDetailTopSection(
    backItemClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .background(
                Brush.verticalGradient(
                    listOf(Color.Black, Color.Transparent)
                )
            ), contentAlignment = Alignment.TopStart
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

@Composable
private fun LoadingProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Center)
        )
    }
}

@Composable
private fun ErrorMessage(state: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = state,
            color = Color.Red,
            modifier = Modifier.align(Center)
        )
    }
}

@Composable
private fun PokemonDetailData(
    state: PokemonDetailState,
    loading: Boolean,
    pokemonImageSize: Dp,
    topPadding: Dp,
) {
    //zIndex kullanarak alttaki olan boxun üstüne aldım.
    // Resim Boxun arkasında kalıyordu
    if (!loading) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .size(pokemonImageSize)
                    .offset(y = topPadding)
                    .zIndex(1f)
                    .align(TopCenter),
                model = state.pokemonDetail?.sprites?.frontDefault,
                contentDescription = "Pokemon Image",
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = topPadding + pokemonImageSize / 2f,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                contentAlignment = TopCenter
            ) {

            }
        }
    }
}