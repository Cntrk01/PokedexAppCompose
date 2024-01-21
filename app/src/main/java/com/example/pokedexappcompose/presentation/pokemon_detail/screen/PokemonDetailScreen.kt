@file:Suppress("DEPRECATION")

package com.example.pokedexappcompose.presentation.pokemon_detail.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedexappcompose.R
import com.example.pokedexappcompose.data.response.Pokemon
import com.example.pokedexappcompose.data.response.Type
import com.example.pokedexappcompose.presentation.pokemon_detail.viewmodel.PokemonDetailViewModel
import com.example.pokedexappcompose.util.parseStatToAbbr
import com.example.pokedexappcompose.util.parseStatToColor
import com.example.pokedexappcompose.util.parseTypeToColor
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.round

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    backItemClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var loading by remember { mutableStateOf(state.loading) }

    LaunchedEffect(loading) {
        if (loading) {
            delay(1000)
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
                    pokemonItem = state.pokemonDetail,
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
    pokemonItem: Pokemon? = null,
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
                model = pokemonItem?.sprites?.frontDefault,
                contentDescription = "Pokemon Image",
            )

            Column(
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
                    .padding(16.dp)
                    .align(TopCenter),
            ) {
                Text(
                    text = "#${pokemonItem?.id} ${
                        pokemonItem?.name?.capitalize(
                            Locale.ROOT
                        )
                    }",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif
                )

                pokemonItem?.types?.let { PokemonTypeSection(type = it) }

                PokemonDetailDataSection(
                    pokemonWeight = pokemonItem?.weight,
                    pokemonHeight = pokemonItem?.height
                )

                if (pokemonItem != null) {
                    PokemonBaseStats(pokemonInfo = pokemonItem)
                }
            }

        }
    }
}

@Composable
private fun PokemonTypeSection(
    type: List<Type>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        for (types in type) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(types))
                    .height(35.dp),
                contentAlignment = Center
            ) {
                Text(
                    text = types.type.name.capitalize(Locale.ROOT),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight: Int?,
    pokemonHeight: Int?,
    sectionHeight: Dp = 80.dp
) {
    val pokemonWeightInKg = remember {
        round(pokemonWeight?.times(100f) ?: 100f) / 1000f
    }
    val pokemonHeightInMeters = remember {
        round(pokemonHeight?.times(100f) ?: 100f) / 1000f
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = "kg",
            dataIcon = painterResource(
                id = R.drawable.ic_weight
            ),
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(1.dp, sectionHeight)
                .background(Color.LightGray)
        )

        PokemonDetailDataItem(
            dataValue = pokemonHeightInMeters,
            dataUnit = "m",
            dataIcon = painterResource(
                id = R.drawable.ic_height
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun PokemonDetailDataItem(
    dataValue: Float,
    dataUnit: String,
    dataIcon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = dataIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$dataValue$dataUnit",
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    //stat içindeki max değeri tutuyoruz ve normal gelen değerleri tutuyoruz.
    //max değeri de bölerek kapladığı alanı gösteriyoruz.Örneğin maxWitdh 1f se stat değer ile max ı bölerek çıkan
    //float değer ile örneğin 0.75 geliyorsa 1 içerisinden 0.75 ini kaplamasını sağlıyoruz
    //curpercent işte bu ne kadar yer kaplayacağını tutmamızı sağlıyor.Ve curPercent ile animasyon hızını da ayarlıyoruz
    //i değeri 0,1,2,3,4 şeklinde bize değer getiriyor
    val curPercent = animateFloatAsState(
        label = "", animationSpec = tween(
            animDuration,
            animDelay
        ), targetValue =
        if (animationPlayed) {
            //bunu sonradan ekledim. 0.23 değerlerinde yazı ile deger üst üste geliyordu bununla birlikte uzunluğu büyüttük
            if ((statValue / statMaxValue.toFloat())<0.25f){
                0.28f
            }else{
                statValue / statMaxValue.toFloat()
            }
        } else 0f
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF505050)
                } else {
                    Color.LightGray
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(text = statName, fontWeight = FontWeight.Bold, modifier = Modifier)

            Text(
                //buradada curpercent ile max olan değeri çarparak her item için ayrılan hp atk
                //değerlerini yerleştirilmesini tekrar sağlıyoruz curPercent.value * statMaxValue yapabiliriz ama gerek yok
                text = statValue.toString(),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun PokemonBaseStats(
    pokemonInfo: Pokemon,
    animDelayPerItem: Int = 100
) {
    val maxBaseStat = remember {
        pokemonInfo.stats.maxOf { it.baseStat }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Base Stats : ",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        for (i in pokemonInfo.stats.indices) {
            val stat = pokemonInfo.stats[i]
            PokemonStat(statName = parseStatToAbbr(stat),
                statValue = stat.baseStat,
                statMaxValue =maxBaseStat,
                statColor = parseStatToColor(stat),
                animDelay = i*animDelayPerItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}