package com.alkss.pokedex.feature_detail.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.alkss.pokedex.core.presentation.LoadingSpinner

@Composable
fun PokemonDetailScreen(
    uiState: PokemonDetailUiState,
    isLoading: Boolean,
    action: (PokemonDetailScreenEvent) -> Unit
) {
    if (isLoading) {
        LoadingSpinner()
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("#${uiState.id}", fontSize = 18.sp)
                if (uiState.isFavorite) {
                    Button(onClick = { action.invoke(PokemonDetailScreenEvent.SwitchFavorite) }) {
                        Text("Remove Favorite")
                    }
                } else {
                    OutlinedButton(onClick = { action.invoke(PokemonDetailScreenEvent.SwitchFavorite) }) {
                        Text("Mark as Favorite")
                    }
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            AsyncImage(
                model = uiState.image,
                contentDescription = uiState.name,
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.CenterHorizontally)
            )
            uiState.name?.let {
                Text(
                    it,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 32.sp
                )
            }
            Text(
                text = "(" + uiState.typeList.joinToString(separator = "/") { it.name } + ")",
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("BaseXP: ${uiState.baseXp}")
                    Text("Weight: ${uiState.weight}")
                    Text("Height: ${uiState.height}")
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Move List",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                HorizontalDivider(thickness = 2.dp)
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    itemsIndexed(uiState.moveList) { _, move ->
                        Text(text = move.name, modifier = Modifier.padding(8.dp))

                        HorizontalDivider(thickness = 2.dp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailPreview() {
    val uiState = PokemonDetailUiState(
        name = "Dona Petty",
        id = 9767,
        weight = 2233,
        image = "hendrerit",
        baseXp = 123,
        height = 123,
        isFavorite = false,
        moveList = listOf(
            Move(
                name = "Move name"
            ),
            Move(
                name = "Move name"
            ),
            Move(
                name = "Move name"
            ),
        ),
        typeList = listOf(
            Type(
                name = "air"
            ),
            Type(
                name = "air"
            ),
        ),
    )

    PokemonDetailScreen(
        uiState = uiState,
        false
    ) {}
}