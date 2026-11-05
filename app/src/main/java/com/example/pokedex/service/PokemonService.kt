package com.example.pokedex.service

import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon/{id}")
    fun getPokemonByNumberDex(@Path("id") id: Int): Call<Pokemon>

    @GET("pokemon?limit=10000&offset=0")
    fun getTodosOsPokemons(): Call<PokemonListResponse>

}