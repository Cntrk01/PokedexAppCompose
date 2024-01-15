package com.example.pokedexappcompose.presentation.pokemon_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedexappcompose.R
import com.example.pokedexappcompose.data.PokemonListEntry
import com.example.pokedexappcompose.presentation.pokemon_list.background_image_color.PokemonEntry

@Composable
fun PokemonListScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )

            PokemonSearch(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, top = 20.dp),
                hint = "Search...",
                onSearch = {

                }
            )
        }
    }
}

@Composable
fun PokemonRow(
    rowIndex: Int,
    entries: List<PokemonListEntry>,
    navController: NavController
) {
    Column {
        Row {
            PokemonEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))

            if (entries.size>=rowIndex*2+2){
                PokemonEntry(
                    entry = entries[rowIndex*2+1],
                    navController = navController,
                    modifier = Modifier.weight(1f))
            }else{
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

