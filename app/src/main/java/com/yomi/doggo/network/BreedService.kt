package com.yomi.doggo.network

import com.yomi.doggo.network.model.Dog
import com.yomi.doggo.util.Endpoints
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
interface BreedService {

    @GET(Endpoints.RANDOM)
    suspend fun getRandom(@Path("breed") breed: String): Response<Dog>

    @GET(Endpoints.BREEDS)
    suspend fun getBreeds(): Response<List<Dog>>
}