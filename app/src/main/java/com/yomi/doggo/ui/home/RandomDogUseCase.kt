package com.yomi.doggo.ui.home

import com.yomi.doggo.data.IRepository
import com.yomi.doggo.network.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
class RandomDogUseCase(private val breedRepo: IRepository) {

    suspend fun getRandomDog(breed: String): Dog {
        return withContext(Dispatchers.IO) {
            breedRepo.getRandomDog(breed)
        }
    }
}