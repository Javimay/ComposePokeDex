package com.javimay.pokedex.data.remote.responses

data class HeldItem(
    val item: Item,
    val versionDetails: List<VersionDetail>
)