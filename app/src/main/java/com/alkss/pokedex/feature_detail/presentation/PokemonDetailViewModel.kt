package com.alkss.pokedex.feature_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkss.pokedex.feature_detail.domain.use_case.DetailUseCases
import com.alkss.pokedex.feature_list.domain.model.local.Moves
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import com.alkss.pokedex.feature_list.domain.model.local.Type as UiType

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val detailUseCases: DetailUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(AtomicBoolean(true))
    val isLoading = _isLoading.asStateFlow()

    private var _pokemonName = MutableStateFlow("")

    fun onEvent(event: PokemonDetailScreenEvent) {
        when (event) {
            PokemonDetailScreenEvent.SwitchFavorite -> {
                updatePokemonFavoriteStatus()
            }
        }
    }

    fun updatePokemonName(pokemonName: String) {
        _pokemonName.update { pokemonName }
        updateDetailState()
    }

    private fun updatePokemonFavoriteStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isFavorite = !it.isFavorite) }

            detailUseCases.switchFavorite.invoke(
                pokemonName = _pokemonName.value,
                isFavorite = _uiState.value.isFavorite
            )
        }
    }

    private fun updateDetailState() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { AtomicBoolean(true) }
            detailUseCases.fetchPokemonByName.invoke(_pokemonName.value)?.let { payload ->
                _uiState.update {
                    PokemonDetailUiState(
                        name = payload.pokemon.name,
                        id = payload.pokemon.order,
                        weight = payload.pokemon.weight,
                        image = payload.pokemon.image,
                        baseXp = payload.pokemon.baseXp,
                        height = payload.pokemon.height,
                        isFavorite = payload.pokemon.isFavorite,
                        moveList = toUiMove(payload.moves),
                        typeList = toUiType(payload.types)
                    )
                }
            }
            _isLoading.update { AtomicBoolean(false) }
        }
    }

    private fun toUiType(types: List<UiType>): List<Type> {
        val typeList = mutableListOf<Type>()
        types.forEach {
            typeList.add(Type(it.name))
        }
        return typeList.distinct().toList()
    }

    private fun toUiMove(moves: List<Moves>): List<Move> {
        val moveList = mutableListOf<Move>()
        moves.forEach {
            moveList.add(Move(it.name))
        }
        return moveList.distinct().toList()
    }
}

