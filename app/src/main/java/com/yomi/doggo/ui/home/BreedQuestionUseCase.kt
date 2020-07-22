package com.yomi.doggo.ui.home

import com.yomi.doggo.data.IRepository
import com.yomi.doggo.network.model.DogResponse
import com.yomi.doggo.ui.model.BreedDetail
import com.yomi.doggo.ui.model.BreedQuestion
import com.yomi.doggo.ui.model.Option
import com.yomi.doggo.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
class BreedQuestionUseCase(private val breedRepo: IRepository) {

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

    private fun getRandomBreed(): BreedDetail? {
        return breedsListCache?.random()
    }

    /**
     * Method to get a randomised list of breeds apart from the @param breed
     */
    private fun getRandomBreeds(count: Int, apartFrom: BreedDetail ): List<BreedDetail> {
        val list =  breedsListCache!!.shuffled().take(count + 1).toMutableList()
        val itemToBeRemoved = list.find { it.name == apartFrom.name }
        if (itemToBeRemoved != null) {
            list.remove(itemToBeRemoved)
        }
        return list.take(count)
    }

    /**
     * Method to create the Question model
     * @param dog  the dog object that contains the image url
     * @param breed the breed the dog belongs to. This will represent the correct answer in the UI
     * @param numberOfOptions total number of options (wrong and correct) we wish to display in the UI
     */
    private fun createBreedQuestion(dog: DogResponse, breed: BreedDetail, numberOfOptions: Int): BreedQuestion {
        val options = getRandomBreeds(numberOfOptions - 1, breed).map { Option(it) }.plus(Option(breed.name, true)).shuffled()
        return BreedQuestion(dog.imageUrl, options)
    }
}