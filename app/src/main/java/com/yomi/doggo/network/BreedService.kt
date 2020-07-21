package com.yomi.doggo.network

import com.yomi.doggo.network.model.Dog
import com.yomi.doggo.util.Endpoints
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
interface BreedService {

    @Headers("Cache-Control: no-cache")
    @GET(Endpoints.RANDOM_DOG)
    suspend fun getRandom(@Path("breed_path", encoded = true) breed: String): Response<Dog>

    @GET(Endpoints.ALL_BREEDS)
    suspend fun getBreeds(): Response<List<Dog>>
}