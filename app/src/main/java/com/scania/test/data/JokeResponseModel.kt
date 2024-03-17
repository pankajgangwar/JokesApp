package com.scania.test.data


import com.google.gson.annotations.SerializedName

data class JokeResponseModel(@SerializedName("amount")
                             val amount: Int = 0,
                             @SerializedName("error")
                             val error: Boolean = false,
                             @SerializedName("message")
                             val message: String = "",
                             @SerializedName("additionalInfo")
                             val additionalInfo: String = "",
                             @SerializedName("jokes")
                             val jokes: List<JokesItem>?) {
    data class JokesItem(@SerializedName("delivery")
                         val delivery: String? = "",
                         @SerializedName("flags")
                         val flags: Flags,
                         @SerializedName("safe")
                         val safe: Boolean = false,
                         @SerializedName("setup")
                         val setup: String? = "",
                         @SerializedName("joke")
                         val joke: String? = "",
                         @SerializedName("id")
                         val id: Int = 0,
                         @SerializedName("category")
                         val category: String = "",
                         @SerializedName("type")
                         val type: String = "",
                         @SerializedName("lang")
                         val lang: String = "") {

        data class Flags(@SerializedName("sexist")
                         val sexist: Boolean = false,
                         @SerializedName("explicit")
                         val explicit: Boolean = false,
                         @SerializedName("religious")
                         val religious: Boolean = false,
                         @SerializedName("nsfw")
                         val nsfw: Boolean = false,
                         @SerializedName("political")
                         val political: Boolean = false,
                         @SerializedName("racist")
                         val racist: Boolean = false)
    }
}