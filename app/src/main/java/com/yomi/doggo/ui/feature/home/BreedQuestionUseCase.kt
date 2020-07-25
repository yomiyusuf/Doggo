package com.yomi.doggo.ui.feature.home

import com.yomi.doggo.data.IRepository
import com.yomi.doggo.network.model.DogResponse
import com.yomi.doggo.ui.model.BreedDetail
import com.yomi.doggo.ui.model.BreedQuestion
import com.yomi.doggo.util.Configuration
import com.yomi.doggo.util.DoggoHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
class BreedQuestionUseCase(
    private val breedRepo: IRepository,
    private  val config: Configuration,
    private val helper: DoggoHelper
) {

    private var breedsListCache: List<BreedDetail>? = null

    suspend fun getBreedQuestion(): BreedQuestion {
        return withContext(Dispatchers.IO) {
            populateBreedsCache()
            val randomBreed = getRandomBreed()
            val dog = getDogForBreed(randomBreed.path)
            val breedsForQuestion = helper.getRandomBreeds(breedsListCache!!, config.NUMBER_OF_OPTIONS - 1, randomBreed)
            helper.createBreedQuestion(randomBreed, dog.imageUrl, breedsForQuestion)
        }
    }

    private suspend fun getDogForBreed(path: String): DogResponse {
        return breedRepo.getDogForBreed(path)
    }

    /**
     * The api response of this call is not expected to change frequently. Definitely not during a user session
     * Make a network call if @breedsListCache has been filled.
     */
    private suspend fun populateBreedsCache() {
         if (breedsListCache == null) {
            val list = mutableListOf<BreedDetail>()
            breedsListCache = withContext(Dispatchers.IO) {
                val breedsFromApi = breedRepo.getBreeds()
                breedsFromApi.forEach {
                    list.addAll(BreedDetail.toBreedDetails(it))
                }
                list
            }
             breedsListCache = list
        }
    }

    private fun getRandomBreed(): BreedDetail {
        return breedsListCache!!.random()
    }
}