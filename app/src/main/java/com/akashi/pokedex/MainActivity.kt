package com.akashi.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavArgument
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akashi.pokedex.presentation.pokemondetail.PokemonDetailScreen
import com.akashi.pokedex.presentation.pokemonlist.PokemonListScreen
import com.akashi.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme(dynamicColor = false) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemon_list_screen") {
                    composable(route = "pokemon_list_screen") {
                        PokemonListScreen(navController = navController)
                    }
                    composable(
                        route = "pokemon_detail_screen/{pokemonName}",
                        arguments = listOf(
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            },
                        )
                    ) {
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                        PokemonDetailScreen(
                            pokemonName = pokemonName?.toLowerCase(Locale.ROOT) ?: "",
                            navController = navController
                        )

                    }
                }
            }
        }
    }
}