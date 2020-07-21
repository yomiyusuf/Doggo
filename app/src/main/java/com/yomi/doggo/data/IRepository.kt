package com.yomi.doggo.data

import com.yomi.doggo.network.model.Breed
import com.yomi.doggo.network.model.Dog

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
interface IRepository {
    suspend fun getRandomDog(breed: String): Dog
    suspend fun getBreeds(): List<Breed>
}