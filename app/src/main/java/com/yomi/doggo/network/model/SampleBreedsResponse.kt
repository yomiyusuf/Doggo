package com.yomi.doggo.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
data class SampleBreedsResponse(
    @SerializedName("message")
    val list: List<String>,

    @SerializedName("status")
    val status: String)