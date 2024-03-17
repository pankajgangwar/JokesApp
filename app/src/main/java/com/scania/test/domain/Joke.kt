package com.scania.test.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Joke(
    val delivery: String = "",
    @Embedded
    val flags: Flags,
    val safe: Boolean = false,
    val setup: String = "",
    val joke : String = "",
    @PrimaryKey
    val id: Int,
    val category: String = "",
    val type: String = "",
    val lang: String = "",
    var favourite : Boolean = false) {

    data class Flags(val sexist: Boolean = false,
                     val explicit: Boolean = false,
                     val religious: Boolean = false,
                     val nsfw: Boolean = false,
                     val political: Boolean = false,
                     val racist: Boolean = false)
}