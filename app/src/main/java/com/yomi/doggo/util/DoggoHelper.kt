package com.yomi.doggo.util

import com.yomi.doggo.ui.model.BreedDetail
import com.yomi.doggo.ui.model.BreedQuestion
import com.yomi.doggo.ui.model.Option

/**
 * Created by Yomi Joseph on 2020-07-24.
 */
class DoggoHelper {

    /**
     * Method to get a randomised list of breeds apart from the @param breed
     */
    fun getRandomBreeds(parentList: List<BreedDetail>, count: Int, apartFrom: BreedDetail): List<BreedDetail> {
        val list =  parentList.shuffled().take(count + 1).toMutableList()
        val itemToBeRemoved = list.find { it.name == apartFrom.name }
        if (itemToBeRemoved != null) {
            list.remove(itemToBeRemoved)
        }
        return list.take(count)
    }

    /**
     * Method to create the Question model
     * @param breed the breed the dog belongs to. This will represent the correct answer in the UI
     * @param imageUrl  the dog image url
     * @param otherBreeds breeds to be used to form the remaining (incorrect)options for the questions
     */
    fun createBreedQuestion(
        breed: BreedDetail,
        imageUrl: String,
        otherBreeds: List<BreedDetail>
    ): BreedQuestion {
        val options = otherBreeds.map { Option(it) }.plus(Option(breed.name, true)).shuffled()
        return BreedQuestion(imageUrl, options)
    }
}