package com.yomi.doggo.util

import com.yomi.doggo.ui.common.TestData
import org.junit.Assert.*
import org.junit.Test

class DoggoHelperTest {
    private val helper = DoggoHelper()
    private val repo = TestData()

    @Test
    fun `check getRandomBreeds returns random list`() {
        val count = 4
        val apartFrom = repo.getBreeds()[0]
        val randomList1 = helper.getRandomBreeds(repo.getBreeds(), count, apartFrom)
        val randomList2 = helper.getRandomBreeds(repo.getBreeds(), count, apartFrom)

        assertEquals(count, randomList1.size) // verify correct sized output
        assertEquals(false, randomList1.contains(apartFrom)) //verify random list does not contain apartFrom
        assertEquals(false, randomList1 == randomList2) //verify generated lists are not equal
    }

    @Test
    fun `check createBreedQuestion returns correctly formed breed question`() {

        val questionBreed = repo.getBreeds()[5]
        val imageUrl = "imageUrl"
        val otherBreeds = repo.getBreeds().subList(0, 2)

        val question = helper.createBreedQuestion(questionBreed, imageUrl, otherBreeds)
        val correctOption = question.options.filter { it.isCorrect }
        val selectedOptions = question.options.filter { it.isSelected }

        assertEquals(imageUrl, question.imageUrl)
        assertEquals(otherBreeds.size + 1, question.options.size) //ensure correct number of options
        assertEquals(1, correctOption.size) //ensure only one option is marked as correct
        assertEquals(questionBreed.name, correctOption[0].name) //ensure the correct breed is marked correct
        assertEquals(0, selectedOptions.size) //ensure no option is selected
    }
}