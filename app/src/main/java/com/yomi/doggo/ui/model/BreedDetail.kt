package com.yomi.doggo.ui.model

import com.yomi.doggo.network.model.Breed

/**
 * Created by Yomi Joseph on 2020-07-20.
 */
data class BreedDetail(val name: String, val path: String, val imageUrl: String? = null) {

    constructor(name: String, imageUrl: String): this(name, "", imageUrl)

    companion object {
        fun toBreedDetails(fromApi: Breed): List<BreedDetail> {
            val list = mutableListOf<BreedDetail>()
            if (fromApi.subBreeds.isEmpty()) {
                list.add(BreedDetail(fromApi.breedName, path = fromApi.breedName.toLowerCase()))
            } else {
                fromApi.subBreeds.forEach {
                    list.add(BreedDetail("$it ${fromApi.breedName}", path = "${fromApi.breedName}/$it"))
                }
            }

            return list
        }
    }
}