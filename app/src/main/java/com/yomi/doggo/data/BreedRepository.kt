package com.yomi.doggo.data

import com.yomi.doggo.network.BreedApiException
import com.yomi.doggo.network.BreedService
import com.yomi.doggo.network.Constants
import com.yomi.doggo.network.model.Breed
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
            //Log errror to (crashlytics?)
            throw BreedApiException(resolveErrorMessage(resp.errorBody().toString()))
        }
    }

    override suspend fun getBreeds(): List<Breed> {
        return listOf(
            Breed("affenpinscher", listOf()),
            Breed("akita", listOf()),
            Breed("basenji", listOf()),
            Breed("australian", listOf("shepherd")),
            Breed("buhund", listOf("norwegian")),
            Breed("bulldog", listOf("boston", "english", "french")),
            Breed("bullterrier", listOf("staffordshire")),
            Breed("cattledog", listOf("australian")),
            Breed("chihuahua", listOf()),
            Breed("hound", listOf("afghan", "basset", "blood", "english", "ibizan", "plott", "walker")),
            Breed("mastiff", listOf("bull", "english", "tibetan")),
            Breed("mountain", listOf("bernese", "swiss"))
        )
    }

    //resolve error message to different more informative messages
    private fun resolveErrorMessage(toString: String): String {
        return "Error occurred. Please refresh"
    }
}