package com.javimay.pokedex.data.remote.responses

data class VersionGroupDetail(
    val levelLearnedAt: Int,
    val moveLearnMethod: MoveLearnMethod,
    val versionGroup: VersionGroup
)