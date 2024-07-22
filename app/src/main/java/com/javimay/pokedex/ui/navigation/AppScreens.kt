package com.javimay.pokedex.ui.navigation

sealed class AppScreens(val route: String) {
    data object ListScreen : AppScreens("pokemon_list_screen")
    data object DetailScreen : AppScreens("pokemon_detail_screen")
}