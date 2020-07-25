package com.yomi.doggo.data

import com.yomi.doggo.network.BreedApiException
import com.yomi.doggo.network.BreedService
import com.yomi.doggo.network.Constants
import com.yomi.doggo.network.model.Breed
import com.yomi.doggo.network.model.DogResponse
import com.yomi.doggo.network.model.SampleBreedsResponse
import com.yomi.doggo.util.toList
import org.json.JSONObject

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
class BreedRepository(private val breedService: BreedService): IRepository {

    override suspend fun getDogForBreed(breed: String): DogResponse {
        val resp = breedService.getRandom(breed)

        return if (resp.isSuccessful && resp.body()?.status == Constants.SUCCESS) {
            resp.body()!!
        } else {
            //Log error to (crashlytics?)
            throw BreedApiException(resolveErrorMessage(resp.errorBody().toString()))
        }
    }

    override suspend fun getBreeds(): List<Breed> {
        val resp = breedService.getAllBreeds()
        return getBreeds(resp.string())
    }

    override suspend fun getSampleBreeds(): SampleBreedsResponse {
        val resp = breedService.getSampleBreeds()

        return if (resp.isSuccessful && resp.body()?.status == Constants.SUCCESS) {
            resp.body()!!
        } else {
            //Log error to (crashlytics?)
            throw BreedApiException(resolveErrorMessage(resp.errorBody().toString()))
        }
    }

    private fun getBreeds(json: String): List<Breed> {
        val list = mutableListOf<Breed>()
        val body = JSONObject(json).getJSONObject("message")

        val iter: Iterator<String> = body.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            val subBreeds = body.getJSONArray(key)
            list.add(Breed(key, subBreeds.toList()))
        }
        return list
    }

    //resolve error message to different more informative messages
    private fun resolveErrorMessage(errorBody: String): String {
        return "Error occurred. Please refresh"
    }
}