package com.yomi.doggo.ui.feature.home

import org.junit.Test
import com.yomi.doggo.data.IRepository
import com.yomi.doggo.network.model.Breed
import com.yomi.doggo.network.model.DogResponse
import com.yomi.doggo.network.model.SampleBreedsResponse
import com.yomi.doggo.util.Configuration
import com.yomi.doggo.util.DoggoHelper
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BreedQuestionUseCaseTest {

    private lateinit var usecase: BreedQuestionUseCase
    @Mock
    private lateinit var repository: TestRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        usecase = BreedQuestionUseCase(repository, Configuration(NUMBER_OF_OPTIONS = 4), DoggoHelper())
    }

    @Test
    fun `test getRandomBreeds`() {
        //val randomBreeds1 = usecase.getRandomBreeds(4, getBreeds()[0])
        //val randomBreeds2 = usecase.getRandomBreeds(4, getBreeds()[0])

        //assertEquals(false, randomBreeds1.contains(getBreeds()[0]))
        //assertNotEquals(randomBreeds1, randomBreeds2)
    }


}

class TestRepository(): IRepository {
    override suspend fun getDogForBreed(breed: String): DogResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getBreeds(): List<Breed> {
        TODO("Not yet implemented")
    }

    override suspend fun getSampleBreeds(): SampleBreedsResponse {
        TODO("Not yet implemented")
    }
}