package com.example.pokedex.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon
import com.example.pokedex.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.runtime.LaunchedEffect
import com.example.pokedex.model.PokemonListItem
import com.example.pokedex.model.PokemonListResponse
import androidx.compose.foundation.lazy.grid.items
import com.example.pokedex.components.CardPokemon

@Composable
fun Pokedex(modifier: Modifier = Modifier){

    var pesquisa by remember { mutableStateOf("") }

    var numeroDex by remember { mutableStateOf(1) }

    var listaPokemons by remember { mutableStateOf<List<PokemonListItem>>(emptyList()) }

    var pokemonState by remember { mutableStateOf<Pokemon?>(null) }

    LaunchedEffect(Unit) { // garante que a busca vai acontecer apenas uma vez, assim que a tela for criada, e não vai travar mais o app.
        val call = RetrofitFactory().getPokemonService().getPokemonByNumberDex(id = numeroDex)
        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                pokemonState = response.body()
                Log.i("TESTE", "${response.body()}")
            }
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.i("TESTE", "${t.message}")
            }
        })
    }

    LaunchedEffect(Unit) {
        val call = RetrofitFactory().getPokemonService().getTodosOsPokemons()
        call.enqueue(object : retrofit2.Callback<PokemonListResponse> {
            override fun onResponse(call: retrofit2.Call<PokemonListResponse>, response: retrofit2.Response<PokemonListResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.i("LISTA", "Count: ${body?.count} | Results: ${body?.results?.size}")
                    listaPokemons = response.body()?.results ?: emptyList()
                }
            }
            override fun onFailure(call: retrofit2.Call<PokemonListResponse>, t: Throwable) {
                Log.i("TESTE", "${t.message}")
            }
        })
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        // -- header --
        Row(
            modifier = Modifier
                .background(Color(0xFFDA3C39))
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 25.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(45.dp)
                    .padding(end = 12.dp),
                painter = painterResource(
                    R.drawable.pokeball_icon
                ),
                contentDescription = null
            )

            Text(
                text = "Pokédex",
                color = Color.White,
                fontSize = 30.sp
            )
        }
        // -- header --

        // -- main --
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
                .weight(1f)
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFDEDEDE),
                        shape = RoundedCornerShape(16.dp)
                    ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                value = pesquisa,
                onValueChange = { pesquisa = it },
                placeholder = {
                    Text(
                        text = "Pokémon ou Número da Dex",
                        color = Color(0xFF767676)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Pesquisar"
                    )
                }
            )
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
//                pokemonState?.let { pokemon ->
//                    Text(
//                        text = "#00${pokemon.numeroDex}",
//                        fontSize = 18.sp
//                    )
//
//                    AsyncImage(
//                        // Monta a URL nova usando o ID do pokémon
//                        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.numeroDex}.png",
//                        contentDescription = "Foto do ${pokemon.pokemonNome}",
//                        modifier = Modifier
//                            .size(200.dp) // Aumentei um pouco o tamanho porque essa arte é maior
//                            .padding(top = 8.dp)
//                    )
//
//                    Text(
//                        text = "${pokemon.pokemonNome}",
//                        modifier = Modifier.padding(top = 16.dp),
//                        fontSize = 24.sp
//                    )
//                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3), // <-- Define 3 colunas
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(listaPokemons) { pokemon ->
                        CardPokemon(pokemon)
                    }
                }
            }
        }
        // -- main --
    }
}