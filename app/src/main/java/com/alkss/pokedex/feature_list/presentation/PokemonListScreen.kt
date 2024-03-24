package com.alkss.pokedex.feature_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.alkss.pokedex.core.presentation.LoadingSpinner
import com.alkss.pokedex.ui.util.Screen

@Composable
fun PokemonListScreen(
    navController: NavController,
    isLoading: Boolean,
    uiState: PokemonListUiState,
    action: (PokemonListScreenEvent) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val offset = remember {
        mutableIntStateOf(8)
    }

    var pokemonName by remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(modifier = Modifier.weight(4f),
                    value = pokemonName,
                    onValueChange = {
                        pokemonName = it
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Button(colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                        alpha = 0.4f
                    ),
                    disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                        alpha = 0.4f
                    )
                ), modifier = Modifier.weight(1.8f), onClick = {
                    action.invoke(
                        PokemonListScreenEvent.SearchByName(pokemonName = pokemonName)
                    )
                }

                ) {
                    Text(text = "Search", textAlign = TextAlign.Center)
                }
            }

            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp)
            ) {
                itemsIndexed(uiState.pokemonList) { index, pokemonUiStates ->
                    LaunchedEffect(lazyListState) {
                        snapshotFlow { lazyListState.firstVisibleItemIndex }.collect { index ->
                            if (index >= offset.intValue) {
                                action.invoke(PokemonListScreenEvent.NextPage)
                                offset.intValue += 20
                            }
                        }
                    }

                    Row(Modifier.fillMaxWidth()) {
                        PokemonEntry(uiState = pokemonUiStates,
                            modifier = Modifier
                                .weight(1f)
                                .padding(6.dp)
                                .clickable {
                                    pokemonUiStates.pokemonName?.let {
                                        navController.navigate(
                                            Screen.PokemonDetail.route + "?pokemonName=${it}"
                                        )
                                    }
                                }
                        )
                    }
                }
            }
        }
        if (isLoading) {
            LoadingSpinner(Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
fun PokemonEntry(modifier: Modifier = Modifier, uiState: PokemonUiState) {
    Box(
        modifier
            .background(MaterialTheme.colorScheme.background)
            .border(width = 1.dp, color = Color.Gray)
            .padding(16.dp)
    ) {
        Text(text = "#${uiState.pokemonNumber}", modifier = Modifier.align(Alignment.TopStart))
        AsyncImage(
            model = uiState.pokemonImageUrl,
            contentDescription = uiState.pokemonName,
            modifier = Modifier
                .size(128.dp)
                .align(Alignment.Center)
        )
        uiState.pokemonName?.let {
            Text(
                text = it, modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

    }
}


@Preview
@Composable
private fun PreviewList(
) {
    PokemonListScreen(
        navController = rememberNavController(), isLoading = true, uiState = PokemonListUiState(
            pokemonList = listOf(
                PokemonUiState(
                    pokemonName = "pokemonName",
                    pokemonImageUrl = "pokemonImageUrl",
                    pokemonNumber = 1
                ), PokemonUiState(
                    pokemonName = "pokemonName",
                    pokemonImageUrl = "pokemonImageUrl",
                    pokemonNumber = 2
                ), PokemonUiState(
                    pokemonName = "pokemonName",
                    pokemonImageUrl = "pokemonImageUrl",
                    pokemonNumber = 3
                ), PokemonUiState(
                    pokemonName = "pokemonName",
                    pokemonImageUrl = "pokemonImageUrl",
                    pokemonNumber = 4
                ), PokemonUiState(
                    pokemonName = "pokemonName",
                    pokemonImageUrl = "pokemonImageUrl",
                    pokemonNumber = 5
                )
            )
        )
    ) {

    }
}