package com.kykers.naplite.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Обрётка под запрос
 */
class ResponseWrapper<T> : Serializable {
    @SerializedName("response")
    val data: T? = null
    @SerializedName("error")
    val error: Error? = null
}