package com.javimay.pokedex.data.remote.responses

data class Ability(
    val ability: AbilityX,
    val isHidden: Boolean,
    val slot: Int
)