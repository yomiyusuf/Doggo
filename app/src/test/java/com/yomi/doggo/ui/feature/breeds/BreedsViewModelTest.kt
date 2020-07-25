package com.yomi.doggo.ui.feature.breeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.yomi.doggo.ui.common.TestContextProvider
import com.yomi.doggo.ui.common.TestCoroutineRule
import com.yomi.doggo.ui.model.BreedDetail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.Error

class BreedsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: BreedsViewModel

    @Mock
    private lateinit var breedsUseCase: BreedsUseCase

    @Mock
    private lateinit var observer: Observer<List<BreedDetail>>
    @Mock
    private lateinit var loadingObserver: Observer<Boolean>
    @Mock
    private lateinit var errorObserver: Observer<Boolean>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = BreedsViewModel(breedsUseCase, TestContextProvider()).apply {
            breeds.observeForever(observer)
            isLoading.observeForever(loadingObserver)
            loadingError.observeForever(errorObserver)
        }
    }

    @Test
    fun `should have data when getSampleBreeds returns proper data`() {
        testCoroutineRule.runBlockingTest {
            val data = listOf(BreedDetail("name", "path", "imageUrl"))
            whenever(breedsUseCase.getSampleBreeds()).thenReturn(data)

            //when
            viewModel.getBreeds()

            //then
            verify(loadingObserver).onChanged(true)
            verify(observer).onChanged(data)
        }

    }

    @Test
    fun `should fail when getSampleBreeds throws error`() {
        testCoroutineRule.runBlockingTest {
            val error = Error()
            whenever(breedsUseCase.getSampleBreeds()).thenThrow(error)

            //when
            viewModel.getBreeds()

            //then
            verify(loadingObserver).onChanged(true)
            verify(errorObserver).onChanged(true)
        }

    }
}