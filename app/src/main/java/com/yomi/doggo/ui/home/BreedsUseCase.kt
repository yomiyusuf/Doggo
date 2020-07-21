package com.yomi.doggo.ui.home

import com.yomi.doggo.data.IRepository
import com.yomi.doggo.network.model.Dog
import com.yomi.doggo.ui.model.BreedDetail
import com.yomi.doggo.ui.model.BreedQuestion
import com.yomi.doggo.ui.model.Option
import com.yomi.doggo.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
class BreedsUseCase(private val breedRepo: IRepository) {

    private var breedsListCache: List<BreedDetail>? = null

    suspend fun getRandomDog(): BreedQuestion {
        return withContext(Dispatchers.IO) {
            populateBreedsCache()

            val randomBreed = getRandomBreed()!!
            createBreedQuestion(breedRepo.getRandomDog(randomBreed.path), randomBreed, Constants.NUMBER_OF_OPTIONS)
        }
    }

    /**
     * The api response of this call is not expected to change frequently. Definitely not during a user session
     * Return the cache @breedsListCache if it has been filled. Else make a network call
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

    private fun getRandomBreed(): BreedDetail? {
        return breedsListCache?.random()
    }

    /**
     * Method to get a randomised list of breeds apart from the @param breed
     */
    private fun getRandomBreeds(count: Int, breed: BreedDetail ): List<BreedDetail> {
        return breedsListCache!!.shuffled(Random(3)).minus(breed).take(count)
    }

    /**
     * Method to create the Question model
     * @param dog  the dog object that contains the image url
     * @param breed the breed the dog belongs to. This will represent the correct answer in the UI
     * @param numberOfOptions total number of options (wrong and correct) we wish to display in the UI
     */
    private fun createBreedQuestion(dog: Dog, breed: BreedDetail, numberOfOptions: Int): BreedQuestion {
        return BreedQuestion(dog.imageUrl, arrayListOf(Option(breed.name, true))).apply {
            this.options.addAll(getRandomBreeds(numberOfOptions - 1, breed).map { Option(it) })
        }.apply {
            options.shuffle()
        }
    }
}