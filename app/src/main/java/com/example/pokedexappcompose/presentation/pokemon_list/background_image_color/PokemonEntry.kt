package com.example.pokedexappcompose.presentation.pokemon_list.background_image_color

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.pokedexappcompose.data.PokemonListEntry
import com.example.pokedexappcompose.presentation.Screen
import com.example.pokedexappcompose.presentation.pokemon_list.viewmodel.PokemonListViewModel
import com.example.pokedexappcompose.ui.theme.RobotoCondensed

@Composable
fun PokemonEntry(
    entry: PokemonListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface

    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(Screen.PokemonDetailScreen.route + "/${dominantColor.toArgb()}" + "/${entry.pokemonName}")
            }
    ) {
        Column {
            //Burada resim data fonksiyonuna yükleniyor.Target bize drawable nesnesi veriyor.
            //Bizde bu nesneyi viewmodeldeki fonksiyonumuza vererek color çıkarıyoruz bu coloru dominantColor içerisine atıyoruz.
            //Emin olmamakla beraber önce bu blok çalışıp dominantColor değişkeni değer alıp sonra box calısıyor.
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = entry.pokemonName,
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary, modifier = Modifier.scale(0.5F)
                    )
                },
                success = { success ->
                    viewModel.calcDominantColor(success.result.drawable){
                        dominantColor = it
                    }
                    SubcomposeAsyncImageContent()
                },
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally)
            )
            Text(
                text = entry.pokemonName,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}