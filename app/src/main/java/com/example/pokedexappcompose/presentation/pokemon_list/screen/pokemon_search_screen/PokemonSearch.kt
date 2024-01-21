package com.example.pokedexappcompose.presentation.pokemon_list.screen.pokemon_search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedexappcompose.presentation.pokemon_list.viewmodel.PokemonListViewModel

@Composable
fun PokemonSearch(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {},
    pokemonListViewModel: PokemonListViewModel= hiltViewModel()
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    if (pokemonListViewModel.state.value.lastQuery.isNotBlank()){
        text=pokemonListViewModel.state.value.lastQuery
        isHintDisplayed=false
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                if (it.isNotBlank()){
                    isHintDisplayed=false
                }
                text = it
                onSearch.invoke(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                //burada hint durumunu kontrol etmem lazım
                .onFocusChanged {
                    //isHintDisplayed = it !=FocusState.Active Active değişkenini görmedi bende onValueChange içinde yaptım.
                }
        )
        //isHintDisplayed hint boş değilse true dönüyor.
        if (isHintDisplayed){
            Text(text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))
        }
    }
}