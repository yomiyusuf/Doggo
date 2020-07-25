package com.yomi.doggo.ui.model

import com.yomi.doggo.network.model.Breed
import org.junit.Assert.*
import org.junit.Test

class BreedDetailTest {
    @Test
    fun `test BreedDetail toBreedDetails`() {
        val breedDetails1 = BreedDetail.toBreedDetails(Breed("hound", listOf("afghan", "basset", "blood", "english", "ibizan", "plott", "walker")))
        val breedDetails2 = BreedDetail.toBreedDetails(Breed("affenpinscher", listOf()))

        assertEquals(7, breedDetails1.size)
        assertEquals(1, breedDetails2.size)
    }
}