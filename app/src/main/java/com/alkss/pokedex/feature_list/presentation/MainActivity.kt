package com.alkss.pokedex.feature_list.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alkss.pokedex.feature_detail.presentation.PokemonDetailScreen
import com.alkss.pokedex.feature_detail.presentation.PokemonDetailViewModel
import com.alkss.pokedex.ui.util.Screen
import com.alkss.pokedex.ui.theme.BaseAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaseAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 50.dp),
                        navController = navController,
                        startDestination = Screen.PokemonList.route
                    ) {
                        composable(
                            route = Screen.PokemonList.route
                        ) {
                            val listViewModel: PokemonListViewModel = hiltViewModel()
                            val isLoading = listViewModel.isLoading.collectAsState().value
                            val uiState = listViewModel.pokemonListUiState.collectAsState().value
                            PokemonListScreen(
                                navController = navController,
                                uiState = uiState,
                                isLoading = isLoading.get()
                            ) {
                                listViewModel.onEvent(it)
                            }
                        }

                        composable(
                            route = Screen.PokemonDetail.route + "?pokemonName={pokemonName}",
                            arguments = listOf(
                                navArgument(
                                    name = "pokemonName"
                                ) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val detailViewModel: PokemonDetailViewModel = hiltViewModel()
                            val pokemonName = it.arguments?.getString("pokemonName")
                            if (pokemonName != null) {
                                LaunchedEffect(pokemonName) {
                                    detailViewModel.updatePokemonName(pokemonName = pokemonName)
                                }
                            } else {
                                navController.navigateUp()
                            }

                            val detailUiState = detailViewModel.uiState.collectAsState().value
                            val isLoading = detailViewModel.isLoading.collectAsState().value
                            PokemonDetailScreen(
                                isLoading = isLoading.get(),
                                uiState = detailUiState
                            ) {
                                detailViewModel.onEvent(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
