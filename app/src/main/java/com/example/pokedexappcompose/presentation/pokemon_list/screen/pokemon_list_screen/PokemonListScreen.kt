package com.example.pokedexappcompose.presentation.pokemon_list.screen.pokemon_list_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedexappcompose.R
import com.example.pokedexappcompose.presentation.pokemon_list.screen.pokemon_search_screen.PokemonSearch
import com.example.pokedexappcompose.presentation.pokemon_list.background_image_color.PokemonEntry
import com.example.pokedexappcompose.presentation.pokemon_list.viewmodel.PokemonListViewModel

@Composable
fun PokemonListScreen(
    onItemClickListener : (Int,String)->Unit,
    viewModel: PokemonListViewModel = hiltViewModel()
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
                    viewModel.searchPokemonList(it)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            PokemonList(onItemClickListener={bgColor,name->
                onItemClickListener.invoke(bgColor,name)
            })
        }
    }
}

@Composable
fun PokemonList(
    viewModel: PokemonListViewModel = hiltViewModel(),
    onItemClickListener : (Int,String)->Unit
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.pokemonList.isNotEmpty()) {
            //bu kodu lazy içerisinden buraya aldım.Kasma yapıyor gibiydi burada olunca daha akıcı görünüm kazandı uygulama
            val itemCount = if (state.pokemonList.size % 2 == 0) {
                state.pokemonList.size / 2
            } else {
                state.pokemonList.size / 2 + 1
            }

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp)
            ) {

                items(itemCount) { pokemonEntry ->
                    if (pokemonEntry >= itemCount - 1 && !state.endReached && !state.isLoading && !state.isSearching) {
                        viewModel.loadPokemonPaging()
                    }
                    PokemonEntry(
                        entry = state.pokemonList[pokemonEntry],
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onItemClickListener = {bgColor,name->
                            onItemClickListener.invoke(bgColor,name)
                        }
                    )
                }
            }
        }

        if (state.error.isNotBlank()) {
            Column(
                modifier = Modifier.align(Center)
            ){
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp).align(CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))
                if (state.error != "Pokemon Is Not Found...") {
                    Button(
                        onClick = {
                            viewModel.loadPokemonPaging()
                        },
                        modifier = Modifier.align(CenterHorizontally)
                    ) {
                        Text(text = "Retry")
                    }
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Center))
        }
    }
}
//Grid olarak göstermek için bunu kullanıyordum fakat kod değişikliğine gittim.
//@Composable
//fun PokemonRow(
//    rowIndex: Int,
//    entries: List<PokemonListEntry>,
//    navController: NavController
//) {
//    Column {
//        Row {
//            PokemonEntry(
//                entry = entries[rowIndex * 2],
//                navController = navController,
//                modifier = Modifier.weight(1f)
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//
//            if (entries.size>=rowIndex*2+2){
//                PokemonEntry(
//                    entry = entries[rowIndex*2+1],
//                    navController = navController,
//                    modifier = Modifier.weight(1f))
//            }else{
//                Spacer(modifier = Modifier.weight(1f))
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//    }
//}
