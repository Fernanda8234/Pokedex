package com.example.pokedex.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonListItem
import com.example.pokedex.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CardPokemon(pokemon: PokemonListItem) {

    var tipos by remember { mutableStateOf<List<String>>(emptyList()) }

    // Roda quando o card aparece na tela (a grade só compõe os itens visíveis)
    LaunchedEffect(pokemon.dex) {
        RetrofitFactory().getPokemonService()
            .getPokemonByNumberDex(pokemon.dex)
            .enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    tipos = response.body()?.tiposNomes ?: emptyList()
                }
                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    Log.i("TESTE", "${t.message}")
                }
            })
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(1.dp, brushDaBorda(tipos), RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "#${pokemon.dex.toString().padStart(3, '0')}",
            fontSize = 10.sp,
            color = Color.Gray,
            textAlign = TextAlign.Right,
            modifier = Modifier.fillMaxWidth(.85f)
        )

        AsyncImage(
            model = pokemon.imagemUrl,
            contentDescription = pokemon.name,
            modifier = Modifier.size(120.dp)
        )

        Text(
            text = pokemon.name.replaceFirstChar { it.uppercase() },
            fontSize = 14.sp,
            maxLines = 1
        )

        // Etiquetas de tipo
        Row(
            modifier = Modifier.padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            tipos.forEach { tipo ->
                Text(
                    text = tipo.replaceFirstChar { it.uppercase() },
                    fontSize = 10.sp,
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(corDoTipo(tipo))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}