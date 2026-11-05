package com.example.pokedex.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    // 1. A URL base da PokéAPI
    private val BASE_URL = "https://pokeapi.co/api/v2/"

    // 2. Configurando o cliente Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Conversor de JSON para Kotlin
        .build()

    // 3. Função que devolve o serviço pronto para uso
    fun getPokemonService(): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }
}