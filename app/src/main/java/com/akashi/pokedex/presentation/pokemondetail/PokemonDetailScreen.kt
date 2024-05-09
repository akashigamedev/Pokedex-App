package com.akashi.pokedex.presentation.pokemondetail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.akashi.pokedex.R
import com.akashi.pokedex.data.remote.responses.pokemon_info.Move
import com.akashi.pokedex.data.remote.responses.pokemon_info.Pokemon
import com.akashi.pokedex.data.remote.responses.pokemon_info.Stat
import com.akashi.pokedex.data.remote.responses.pokemon_info.Type
import com.akashi.pokedex.data.remote.responses.pokemon_species.PokemonSpecies
import com.akashi.pokedex.ui.theme.LightGray
import com.akashi.pokedex.ui.theme.LightRed
import com.akashi.pokedex.utils.Resource
import com.akashi.pokedex.utils.parseStatToAbbr
import com.akashi.pokedex.utils.parseTypeToColor
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import java.util.Locale
import kotlin.math.round
import kotlin.math.roundToInt

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
                    PokemonDetailTopSection(
                        pokemon = pokemonInfo.data,
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.1f)
                    )
                    PokemonDetailStateWrapper(
                        pokemon = pokemonInfo.data,
                        pokemonSpecies = pokemonSpecies,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = topPadding + pokemonImageSize / 2f + 25.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            )
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White),
                        primaryColor = primaryColor,
                    )
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
                        .size(pokemonImageSize)
                        .align(Alignment.TopCenter)
                        .offset(y = topPadding + 50.dp)
                ) {
                    CircularProgressIndicator(
                        color = LightRed,
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.TopCenter)
                            .offset(y = topPadding + 50.dp)
                    )
                }
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
fun PokemonDetailStateWrapper(
    pokemon: Pokemon,
    pokemonSpecies: Resource<PokemonSpecies>,
    modifier: Modifier,
    primaryColor: Color,
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        PokemonTypeSection(types = pokemon.types)
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
            pokemonWeight = pokemon.weight,
            pokemonHeight = pokemon.height,
            pokemonMoves = pokemon.moves,
        )
        Spacer(modifier = Modifier.height(32.dp))
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
                    text = pokemonSpecies.data!!.flavor_text_entries[0].flavor_text.replace(
                        "\n",
                        " "
                    ).lowercase().capitalize(Locale.ROOT),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        PokemonBaseStats(pokemon = pokemon, primaryColor = primaryColor)
    }
}

@Composable
fun PokemonDetailTopSection(pokemon: Pokemon, navController: NavController, modifier: Modifier) {
    Row(
        modifier = modifier.padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigateUp()
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = pokemon.name.capitalize(Locale.ROOT),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
        }
        val paddedId = pokemon.id.toString().padStart(3, '0')
        Text(
            text = "#${paddedId}",
            color = Color.White,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold
        )

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
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )
                    }
                } else {
                    for (move in pokemonMoves) {
                        Text(
                            text = move.move.name.capitalize(Locale.ROOT),
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )
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
            Text(
                text = "$dataValue $dataUnit",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
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
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PokemonStat(
    stat: Stat,
    statColor: Color,
    height: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {

    val statValue = stat.base_stat
    var statMaxValue by remember {
        mutableStateOf(statValue)
    }

    statMaxValue = if(stat.stat.name == "hp") {
        (statValue * 2 + 204)
    } else {
        ((statValue * 2 + 99) * 1.1).roundToInt()
    }

    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / statMaxValue.toFloat()
        } else 0f,
        label = "statAnimate",
        animationSpec = tween(
            animDuration, animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(LightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
        }
    }
}


@Composable
fun PokemonBaseStats(
    pokemon: Pokemon, animDelayPerItem: Int = 100, primaryColor: Color
) {

    Column {
        Text(
            text = "Base Stats",
            color = primaryColor,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                for (stat in pokemon.stats) {
                    Text(
                        text = parseStatToAbbr(stat),
                        modifier = Modifier
                            .fillMaxWidth(0.15f),
                        textAlign = TextAlign.End
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                for (stat in pokemon.stats) {
                    val paddedStatValue = stat.base_stat.toString().padStart(3, '0')
                    Text(text = paddedStatValue)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var i = 0
                for (stat in pokemon.stats) {
                    i++
                    PokemonStat( stat = stat, statColor = primaryColor, animDelay = animDelayPerItem * i)
                }
            }
        }
    }
}