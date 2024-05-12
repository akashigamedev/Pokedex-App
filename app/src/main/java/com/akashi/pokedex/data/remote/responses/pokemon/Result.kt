package com.akashi.pokedex.data.remote.responses.pokemon

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)