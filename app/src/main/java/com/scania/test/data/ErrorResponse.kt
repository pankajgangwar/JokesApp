package com.scania.test.data


import com.google.gson.annotations.SerializedName

data class ErrorResponse(@SerializedName("code")
                         val code: Int = 0,
                         @SerializedName("additionalInfo")
                         val additionalInfo: String = "",
                         @SerializedName("causedBy")
                         val causedBy: List<String>?,
                         @SerializedName("internalError")
                         val internalError: Boolean = false,
                         @SerializedName("error")
                         val error: Boolean = false,
                         @SerializedName("message")
                         val message: String = "",
                         @SerializedName("timestamp")
                         val timestamp: Long = 0)


