package com.yomi.doggo.data

import com.yomi.doggo.network.model.Breed
import com.yomi.doggo.network.model.DogResponse
import com.yomi.doggo.network.model.SampleBreedsResponse

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
interface IRepository {
    suspend fun getDogForBreed(breed: String): DogResponse
    suspend fun getBreeds(): List<Breed>
    suspend fun getSampleBreeds(): SampleBreedsResponse
}