package com.example.pokedex.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor

fun corDoTipo(tipo: String): Color {
    return when (tipo) {
        "grass" -> Color(0xFF78C850)
        "fire" -> Color(0xFFF08030)
        "water" -> Color(0xFF6890F0)
        "bug" -> Color(0xFFA8B820)
        "normal" -> Color(0xFFA8A878)
        "poison" -> Color(0xFFA040A0)
        "electric" -> Color(0xFFF8D030)
        "ground" -> Color(0xFFE0C068)
        "fairy" -> Color(0xFFEE99AC)
        "fighting" -> Color(0xFFC03028)
        "psychic" -> Color(0xFFF85888)
        "rock" -> Color(0xFFB8A038)
        "ghost" -> Color(0xFF705898)
        "ice" -> Color(0xFF98D8D8)
        "dragon" -> Color(0xFF7038F8)
        "dark" -> Color(0xFF705848)
        "steel" -> Color(0xFFB8B8D0)
        "flying" -> Color(0xFFA890F0)
        else -> Color.Gray
    }
}

fun brushDaBorda(tipos: List<String>): Brush {
    return when (tipos.size) {
        0 -> SolidColor(Color(0xFFDEDEDE)) // enquanto carrega
        1 -> SolidColor(corDoTipo(tipos[0]))
        else -> Brush.linearGradient(
            colorStops = arrayOf(
                0.0f to corDoTipo(tipos[0]),
                0.5f to corDoTipo(tipos[0]),
                0.5f to corDoTipo(tipos[1]),
                1.0f to corDoTipo(tipos[1])
            ),
            start = Offset(0f, 0f),
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY) // diagonal 45°
        )
    }
}