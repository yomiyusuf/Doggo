package com.yomi.doggo.ui.feature.breeds

import com.yomi.doggo.data.IRepository
import com.yomi.doggo.ui.model.BreedDetail
import com.yomi.doggo.util.getBreedNameFromUrl

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
class BreedsUseCase(private val breedRepo: IRepository) {

    suspend fun getSampleBreeds(): List<BreedDetail> {
        return breedRepo.getSampleBreeds().list.map { BreedDetail(it.getBreedNameFromUrl(), it) }
    }

}