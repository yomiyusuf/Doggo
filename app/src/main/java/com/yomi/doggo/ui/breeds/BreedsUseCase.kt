package com.yomi.doggo.ui.breeds

import com.yomi.doggo.data.IRepository
import com.yomi.doggo.network.model.DogResponse
import com.yomi.doggo.ui.model.BreedDetail
import com.yomi.doggo.ui.model.BreedQuestion
import com.yomi.doggo.ui.model.Option
import com.yomi.doggo.util.Constants
import com.yomi.doggo.util.getBreedNameFromUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
class BreedsUseCase(private val breedRepo: IRepository) {

    suspend fun getSampleBreeds(): List<BreedDetail> {
        return withContext(Dispatchers.IO) {
            breedRepo.getSampleBreeds().list.map { BreedDetail(it.getBreedNameFromUrl(), it) }
        }
    }

}