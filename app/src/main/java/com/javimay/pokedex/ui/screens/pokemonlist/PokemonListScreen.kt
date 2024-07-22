package com.javimay.pokedex.ui.screens.pokemonlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.javimay.pokedex.R
import com.javimay.pokedex.data.models.PokedexListEntry
import com.javimay.pokedex.ui.navigation.AppScreens
import com.javimay.pokedex.ui.theme.DarkGrey
import com.javimay.pokedex.ui.theme.LightGrey
import com.javimay.pokedex.ui.theme.Roboto
import com.javimay.pokedex.ui.theme.RobotoCondensed

@Composable
fun PokemonListScreen(
    navController: NavHostController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            SearchBar(
                hint = "Search..",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                viewModel = viewModel
            ) {
                viewModel.searchPokemonList(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String = "",
    viewModel: PokemonListViewModel,
    onSearch: (String) -> Unit = {}
) {
    val textSearched by viewModel.textSearch.collectAsState()
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "" && text.isEmpty())
    }
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(5.dp, shape = CircleShape)
            .background(Color.White, CircleShape)
    ) {
        text = textSearched
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                viewModel.updateTextSearch(it)
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = LightGrey,
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = 20.dp)
                .align(CenterStart)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }
                .fillMaxWidth()
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        } else {
            IconButton(onClick = {
                text = ""
                viewModel.updateTextSearch("")
                isHintDisplayed = true
                focusManager.clearFocus()
                onSearch(text)
            }, content = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear search",
                    tint = Color.Black
                )
            }, modifier = Modifier.align(TopEnd)
            )
        }
    }
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (pokemonList.size % 2 == 0) {
            pokemonList.size / 2
        } else {
            pokemonList.size / 2 + 1
        }
        items(itemCount) {
            if (it >= itemCount - 1 && !endReached && !isLoading && !isSearching) {
                viewModel.loadPokemonPaginated()
            }
            PokedexRow(rowIndex = it, entries = pokemonList, navController = navController)
        }
    }
    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadPokemonPaginated()
            }
        }
    }
}

@Composable
fun PokedexRow(rowIndex: Int, entries: List<PokedexListEntry>, navController: NavController) {
    Column {
        Row {
            PokemonCard(
                entry = entries[rowIndex * 2], navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                PokemonCard(
                    entry = entries[rowIndex * 2 + 1], navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PokemonCard(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = DarkGrey
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    var isloading by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor, defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    AppScreens.DetailScreen.route +
                            "/${dominantColor.toArgb()}/${entry.pokemonName}"
                )
            }
    ) {
        Text(
            text = entry.number.toString(), fontSize = 25.sp,
            fontFamily = Roboto, fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .padding(start = 7.dp)
                .align(Alignment.TopStart)
        )
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier
                    .align(CenterHorizontally),
                contentAlignment = Center
            ) {
                if (isloading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.scale(0.5f)
                    )
                }
                AsyncImage(
                    contentDescription = entry.pokemonName,
                    model =
                    ImageRequest.Builder(LocalContext.current)
                        .data(entry.imageUrl)
                        .build(),
                    modifier = Modifier.size(120.dp),
                    onSuccess = {
                        isloading = false
                        viewModel.calcDominantColor(it.result.drawable) { color ->
                            dominantColor = color
                        }
                    },
                    onLoading = {
                        isloading = true
                    }
                )
            }
            Text(
                text = entry.pokemonName,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun RetrySection(error: String, onRetry: () -> Unit) {
    Column {
        Text(text = error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}
