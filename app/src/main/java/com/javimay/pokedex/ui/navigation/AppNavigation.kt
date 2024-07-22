package com.javimay.pokedex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.javimay.pokedex.ui.screens.pokemondetails.DetailScreen
import com.javimay.pokedex.ui.screens.pokemonlist.PokemonListScreen

const val DOMINANT_COLOR = "dominantColor"
const val POKEMON_NAME = "pokemonName"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.ListScreen.route) {
        composable(AppScreens.ListScreen.route) {
            PokemonListScreen(navController = navController)
        }
        composable(AppScreens.DetailScreen.route + "/{$DOMINANT_COLOR}/{$POKEMON_NAME}",
            arguments = listOf(
                navArgument(DOMINANT_COLOR) {
                    type = NavType.IntType
                },
                navArgument(POKEMON_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            val dominantColor = remember {
                val color = it.arguments?.getInt(DOMINANT_COLOR)
                color?.let { Color(it) } ?: Color.White
            }
            val pokemonName = remember {
              it.arguments?.getString(POKEMON_NAME)
            }
            DetailScreen(
                dominantColor = dominantColor,
                pokemonName = pokemonName ?: "",
                navController = navController
            )
        }
    }
}