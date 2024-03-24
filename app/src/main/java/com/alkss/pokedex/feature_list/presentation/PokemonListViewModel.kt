package com.alkss.pokedex.feature_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkss.pokedex.feature_list.data.data_source.toUiState
import com.alkss.pokedex.feature_list.domain.use_case.ListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject


@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val useCases: ListUseCases
) : ViewModel() {

    private var _currentPointer = 0
    private var job: Job? = null

    private val _isLoading = MutableStateFlow<AtomicBoolean>(AtomicBoolean(true))
    val isLoading = _isLoading.asStateFlow()

    private val _pokemonListUiState = MutableStateFlow(PokemonListUiState())
    val pokemonListUiState = _pokemonListUiState.asStateFlow()

    init {
        onEvent(PokemonListScreenEvent.Refresh)
    }

    fun onEvent(event: PokemonListScreenEvent) {
        when (event) {
            PokemonListScreenEvent.Refresh -> {
                _currentPointer = 0
                _pokemonListUiState.update { PokemonListUiState() }
                fetchPokemon()
            }

            PokemonListScreenEvent.NextPage -> {
                _currentPointer += 20
                fetchPokemon()
            }

            is PokemonListScreenEvent.SearchByName -> {
                searchByName(event.pokemonName)
            }
        }
    }


    private fun searchByName(pokemonName: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { AtomicBoolean(true) }

            if (isActive.not()) {
                return@launch
            }

            if (pokemonName.isBlank() || pokemonName.isEmpty()) {
                onEvent(PokemonListScreenEvent.Refresh)
            } else {
                val pokemonData = useCases.getLocalPokemonByName.invoke(pokemonName = pokemonName)
                _pokemonListUiState.update { it.copy(pokemonList = pokemonData.toUiState()) }
            }
            _isLoading.update { AtomicBoolean(false) }
        }
    }

    private fun fetchPokemon() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { AtomicBoolean(true) }

            if (isActive.not()) {
                return@launch
            }

            val pokemonData = useCases.fetchPokemonRequest.invoke(_currentPointer)

            if (pokemonData != null) {
                _pokemonListUiState.update {
                    it.copy(
                        pokemonList = it.pokemonList + pokemonData.toUiState()
                    )
                }
            }
            _isLoading.update { AtomicBoolean(false) }
        }
    }
}