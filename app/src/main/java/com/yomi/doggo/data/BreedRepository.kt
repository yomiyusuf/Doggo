package com.yomi.doggo.data

import com.yomi.doggo.network.BreedApiException
import com.yomi.doggo.network.BreedService
import com.yomi.doggo.network.Constants
import com.yomi.doggo.network.model.Dog

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
class BreedRepository(private val breedService: BreedService): IRepository {
    override suspend fun getRandomDog(breed: String): Dog {
        val resp = breedService.getRandom(breed)

        return if (resp.isSuccessful && resp.body()?.status == Constants.SUCCESS) {
            resp.body()!!
        } else {
            throw BreedApiException(resolveErrorMessage(resp.errorBody().toString()))
        }
    }

    override suspend fun getBreeds(): List<Dog> {
        TODO("Not yet implemented")
    }

    private fun resolveErrorMessage(toString: String): String {
        //resolve error message to different more informative messages
        return "Error occurred. Please refresh"
    }
}