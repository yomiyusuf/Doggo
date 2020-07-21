package com.yomi.doggo.util

object Endpoints {
    const val BASE_URL =  "https://dog.ceo/api/"

    const val  ALL_BREEDS = "breeds/list/all"
    const val RANDOM_DOG = "breed/{breed_path}/images/random"
    const val SAMPLE_BREEDS = "breeds/image/random/50"
}

const val TIME_OUT : Long = 120