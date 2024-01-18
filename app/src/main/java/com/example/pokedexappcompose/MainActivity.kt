package com.example.pokedexappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedexappcompose.common.Constants.DOMINANT_COLOR
import com.example.pokedexappcompose.common.Constants.POKEMON_NAME
import com.example.pokedexappcompose.presentation.pokemon_detail.screen.PokemonDetailScreen
import com.example.pokedexappcompose.presentation.pokemon_list.screen.pokemon_list_screen.PokemonListScreen
import com.example.pokedexappcompose.ui.theme.PokedexAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexAppComposeTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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
                            route = "${Screen.PokemonDetailScreen.route}/{$DOMINANT_COLOR}/{$POKEMON_NAME}",
                            arguments = listOf(
                                navArgument(name = DOMINANT_COLOR) {
                                    type = NavType.IntType
                                })
                        ) {
                            val dominantColor = remember {
                                val color = it.arguments?.getInt(DOMINANT_COLOR)
                                color?.let { Color(it) } ?: Color.White
                            }

                            PokemonDetailScreen(
                                dominantColor = dominantColor,
                                backItemClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
