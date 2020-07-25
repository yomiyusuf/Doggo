package com.yomi.doggo.ui.common

import com.yomi.doggo.ui.model.BreedDetail

/**
 * Created by Yomi Joseph on 2020-07-24.
 */
class TestData() {
    fun getBreeds(): List<BreedDetail> {
        return listOf<BreedDetail>(
            BreedDetail("affenpinscher", "", ""),
            BreedDetail("basenji", "", ""),
            BreedDetail("boxer", "", ""),
            BreedDetail("brabancon", "", ""),
            BreedDetail("cairn", "", ""),
            BreedDetail("chihuahua", "", ""),
            BreedDetail("havanese", "", ""),
            BreedDetail("malinois", "", "")
        )
    }
}