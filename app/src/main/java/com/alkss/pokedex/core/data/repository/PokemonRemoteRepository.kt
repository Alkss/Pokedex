package com.alkss.pokedex.core.data.repository

import com.alkss.pokedex.core.NetworkResult
import com.alkss.pokedex.core.data.remote.endpoint.PokedexApi
import com.alkss.pokedex.feature_detail.domain.model.remote.Pokemon
import com.alkss.pokedex.feature_list.domain.model.remote.PokemonList
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRemoteRepository @Inject constructor(
    private val api: PokedexApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): NetworkResult<PokemonList> {
        val response = try {
            api.getPokemonList(limit = limit, offset = offset)
        } catch (e: Exception){
            return NetworkResult.Error(e)
        }
        return NetworkResult.Success(response)
    }

    suspend fun getPokemon(name: String): NetworkResult<Pokemon> {
        val response = try {
            api.getPokemon(name)
        } catch (e: Exception){
            return NetworkResult.Error(e)
        }
        return NetworkResult.Success(response)
    }
}