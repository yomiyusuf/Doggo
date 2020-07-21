package com.yomi.doggo.ui.model

import com.yomi.doggo.network.model.Breed

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
class BreedDetail(val name: String, val path: String) {

    companion object {
        fun toBreedDetails(fromApi: Breed): List<BreedDetail> {
            val list = mutableListOf<BreedDetail>()
            if (fromApi.subBreeds.isEmpty()) {
                list.add(BreedDetail(fromApi.breedName, fromApi.breedName.toLowerCase()))
            } else {
                fromApi.subBreeds.forEach {
                    list.add(BreedDetail("$it ${fromApi.breedName}", "${fromApi.breedName}/$it"))
                }
            }

            return list
        }
    }
}