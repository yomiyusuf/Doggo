package com.yomi.doggo.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
data class Dog(
    @SerializedName("message")
    val imageUrl: String,

    @SerializedName("status")
    val status: String)