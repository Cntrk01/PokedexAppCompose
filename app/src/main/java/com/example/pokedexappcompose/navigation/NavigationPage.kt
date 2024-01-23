package com.example.pokedexappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokedexappcompose.common.Constants
import com.example.pokedexappcompose.presentation.pokemon_detail.screen.PokemonDetailScreen
import com.example.pokedexappcompose.presentation.pokemon_list.screen.pokemon_list_screen.PokemonListScreen

@Composable
fun NavigationPage(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.PokemonListScreen.route
    ) {

        composable(route = Screen.PokemonListScreen.route) {
            PokemonListScreen(onItemClickListener = { bgColor, pokeName ->
                navController.navigate(route = Screen.PokemonDetailScreen.route+"/${bgColor}"+"/${pokeName}")
            })
        }

        composable(
            //yada şeklindede çalışıyor
            // route = "${Screen.PokemonDetailScreen.route}/{dominantColor}/{pokemonName}",
            route = "${Screen.PokemonDetailScreen.route}/{${Constants.DOMINANT_COLOR}}/{${Constants.POKEMON_NAME}}",
            arguments = listOf(
                navArgument(name = Constants.DOMINANT_COLOR) {
                    type = NavType.IntType
                })
        ) {
            val dominantColor = remember {
                val color = it.arguments?.getInt(Constants.DOMINANT_COLOR)
                color?.let { Color(it) } ?: Color.White
            }
            //window?.statusBarColor = getColor(R.color.black)
            //window.navigationBar=getColor(R.color.black) rengini de değişebiliriz.
            PokemonDetailScreen(
                dominantColor = dominantColor,
                backItemClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}