package com.akashi.pokedex.presentation.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.akashi.pokedex.R
import com.akashi.pokedex.data.remote.responses.pokemon_info.Move
import com.akashi.pokedex.data.remote.responses.pokemon_info.Pokemon
import com.akashi.pokedex.data.remote.responses.pokemon_info.Type
import com.akashi.pokedex.data.remote.responses.pokemon_species.PokemonSpecies
import com.akashi.pokedex.ui.theme.LightGray
import com.akashi.pokedex.ui.theme.LightRed
import com.akashi.pokedex.utils.Resource
import com.akashi.pokedex.utils.parseTypeToColor
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import java.util.Locale
import kotlin.math.round

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value
    val pokemonSpecies = produceState<Resource<PokemonSpecies>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonSpecies(pokemonName)
    }.value
    val context = LocalContext.current


    when (pokemonInfo) {
        is Resource.Success -> {
            val primaryColor = parseTypeToColor(pokemonInfo.data!!.types[0])
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(primaryColor)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = pokemonName.capitalize(Locale.ROOT),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize
                            )
                        }
                        Text(
                            text = "#${pokemonInfo.data.id}",
                            color = Color.White,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = pokemonImageSize / 1.4f, start = 8.dp, end = 8.dp, bottom = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                    ) {
                        Spacer(modifier = Modifier.height(50.dp))
                        PokemonTypeSection(types = pokemonInfo.data.types)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "About",
                            color = primaryColor,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        PokemonDetailDataSection(
                            pokemonWeight = pokemonInfo.data.weight,
                            pokemonHeight = pokemonInfo.data.height,
                            pokemonMoves = pokemonInfo.data.moves,
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        when (pokemonSpecies) {
                            is Resource.Error -> {
                                Text(
                                    text = pokemonSpecies.message!!,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }

                            is Resource.Loading -> {
                                CircularProgressIndicator(
                                    color = LightRed, modifier = Modifier
                                        .size(25.dp)
                                        .padding(horizontal = 16.dp)
                                )
                            }

                            is Resource.Success -> {
                                Text(
                                    text = pokemonSpecies.data!!.flavor_text_entries[0].flavor_text.replace("\n", " "),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Base Stats",
                            color = primaryColor,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.pokeball),
                    contentDescription = null,
                    tint = Color(0x22FFFFFF),
                    modifier = Modifier
                        .padding(9.dp)
                        .size(250.dp)
                        .align(Alignment.TopEnd)
                )
                val paddedNumber = pokemonInfo.data.id.toString().padStart(3, '0')
                val url =
                    "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/${paddedNumber}.png"
                CoilImage(
                    imageRequest = {
                        ImageRequest.Builder(context).data(url).build()
                    },
                    imageOptions = ImageOptions(contentDescription = pokemonInfo.data.name),
                    modifier = Modifier
                        .padding(80.dp)
                        .size(pokemonImageSize)
                        .align(Alignment.TopCenter)
                )
            }
        }

        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = LightRed, modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
        }

        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = pokemonInfo.message!!,
                    color = Color.Red,
                )

            }
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight: Int,
    pokemonHeight: Int,
    pokemonMoves: List<Move>,
    sectionHeight: Dp = 80.dp,
) {
    val pokemonWeightInKG = remember {
        round(pokemonWeight * 100f) / 1000f
    }
    val pokemonHeightInMeters = remember {
        round(pokemonHeight * 100f) / 1000f
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(sectionHeight),
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally)
    ) {
        PokemonDetailDataItem(
            itemLabel = "Weight",
            dataValue = pokemonWeightInKG,
            dataUnit = "kg",
            dataIcon = painterResource(
                id = R.drawable.weight
            )
        )
        Spacer(
            modifier = Modifier
                .size(1.dp, sectionHeight)
                .background(LightGray)
        )
        PokemonDetailDataItem(
            itemLabel = "Height",
            dataValue = pokemonHeightInMeters,
            dataUnit = "m",
            dataIcon = painterResource(
                id = R.drawable.height
            )
        )
        Spacer(
            modifier = Modifier
                .size(1.dp, sectionHeight)
                .background(LightGray)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp)
        ) {
            Column(
            ) {
                if (pokemonMoves.size > 2) {
                    for (i in 1..2) {
                        Text(
                            text = pokemonMoves[i].move.name.capitalize(Locale.ROOT),
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    }
                } else {
                    for (move in pokemonMoves) {
                        Text(text = move.move.name.capitalize(Locale.ROOT))
                    }
                }
            }
            Text(
                text = "Moves",
                color = Color.LightGray,
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            )
        }
    }
}

@Composable
fun PokemonDetailDataItem(
    itemLabel: String, dataValue: Float, dataUnit: String, dataIcon: Painter
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = dataIcon, contentDescription = null, tint = Color.Black)
            Text(text = "$dataValue $dataUnit")
        }
        Text(
            text = itemLabel,
            color = Color.LightGray,
            fontSize = MaterialTheme.typography.labelLarge.fontSize
        )
    }
}

@Composable
fun PokemonTypeSection(
    types: List<Type>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        modifier = Modifier.fillMaxWidth()
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = type.type.name.capitalize(Locale.ROOT),
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}