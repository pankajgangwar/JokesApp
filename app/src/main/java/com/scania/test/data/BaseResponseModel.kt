package com.scania.test.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Failure(
    val message: String = "",
    val status: Int = 0
) : Parcelable

@Parcelize
open class Success : Parcelable {
    val message: String = ""
    val status: Int = 0
}