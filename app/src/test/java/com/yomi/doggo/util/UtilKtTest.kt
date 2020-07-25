package com.yomi.doggo.util

import android.os.Build
import android.widget.Button
import com.yomi.doggo.ui.MainActivity
import com.yomi.doggo.ui.model.Option
import org.json.JSONArray
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class UtilKtTest {

    @Test
    fun `check String capitalizeWords`() {
        assertEquals("One Two", "one two".capitalizeWords())
        assertEquals("One Two Three", "ONE two THree".capitalizeWords())
    }

    @Test
    fun `check that Button updateState correctly updates the button view`() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        val button = Button(activity)
        val option = Option("Breed name", isCorrect = true, isSelected = false)
        val optionSelected = Option("Breed name", isCorrect = true, isSelected = true)

        button.updateState(option)
        assertEquals(true, button.isEnabled)

        button.updateState(optionSelected)
        assertEquals(false, button.isEnabled)
    }

    @Test
    fun `check that getBreedNameFromUrl returns correct name`() {
        val urlWithSubBreed = "https://images.dog.ceo/breeds/retriever-flatcoated/n02099267_1624.jpg"
        val urlWithoutSubBreed = "https://images.dog.ceo/breeds/pembroke/n02113023_4312.jpg"

        assertEquals("flatcoated retriever", urlWithSubBreed.getBreedNameFromUrl())
        assertEquals("pembroke", urlWithoutSubBreed.getBreedNameFromUrl())
    }

    @Test
    fun `check that JSONArray toList() returns correct list`() {
        val jsonArray = JSONArray("[\"one\", \"two\"]")

        assertEquals(2, jsonArray.toList().size)
        assertEquals(true, jsonArray.toList().contains("one"))
        assertEquals(true, jsonArray.toList().contains("two"))
    }
}