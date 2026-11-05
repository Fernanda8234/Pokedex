package com.example.pokedex.model

import com.google.gson.annotations.SerializedName

data class Sprites (
    @SerializedName("front_default")
    val pokemonFoto: String = "" // Deixar com valor padrão vazio evita nulo
)

data class TipoSlot (
    @SerializedName("slot")
    val slot: Int = 0,

    @SerializedName("type")
    val type: TipoInfo? = null
)

data class TipoInfo (
    @SerializedName("name")
    val nome: String = "" // Ex: "grass", "fire", "water"
)

data class Pokemon (
    @SerializedName("id")
    val numeroDex: Int = 0,

    @SerializedName("name")
    val pokemonNome: String = "",

    @SerializedName("sprites")
    val sprites: Sprites? = null,

    @SerializedName("types")
    val tipos: List<TipoSlot> = emptyList()
) {
    val tiposNomes: List<String>
        get() = tipos.sortedBy { it.slot }.mapNotNull { it.type?.nome }
}