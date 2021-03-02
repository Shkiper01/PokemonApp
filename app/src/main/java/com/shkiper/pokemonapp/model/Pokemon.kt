package com.shkiper.pokemonapp.model

import com.google.gson.annotations.SerializedName

data class Pokemon(val id: String,
                   val name: String,
                   val height: String,
                   val weight: String,
                   val description: String){

    fun getImageUrl(): String{
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
    }
}