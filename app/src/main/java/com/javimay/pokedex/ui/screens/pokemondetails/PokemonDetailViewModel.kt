package com.javimay.pokedex.ui.screens.pokemondetails

import androidx.lifecycle.ViewModel
import com.javimay.pokedex.data.remote.responses.Pokemon
import com.javimay.pokedex.repository.PokemonRepository
import com.javimay.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> =
        repository.getPokemonInfo(pokemonName.replaceFirstChar { it.lowercase() })
}