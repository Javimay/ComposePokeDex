package com.javimay.pokedex.util

fun getPokemonImageUrl(pokemonNumber: String): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonNumber}.png"
