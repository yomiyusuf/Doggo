package com.yomi.doggo.ui.dashboard

import com.yomi.doggo.data.IRepository
import com.yomi.doggo.network.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
class BreedsUseCase(private val breedRepo: IRepository) {
    suspend fun getBreeds() : List<Dog> {
        return withContext(Dispatchers.IO) {
            breedRepo.getBreeds()
        }
    }
}