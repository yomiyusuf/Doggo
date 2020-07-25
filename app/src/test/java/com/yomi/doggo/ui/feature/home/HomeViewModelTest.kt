package com.yomi.doggo.ui.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.yomi.doggo.ui.common.TestContextProvider
import com.yomi.doggo.ui.common.TestCoroutineRule
import com.yomi.doggo.ui.model.BreedQuestion
import com.yomi.doggo.ui.model.Option
import com.yomi.doggo.util.Configuration
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.Error

class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var useCase: BreedQuestionUseCase

    @Mock
    private lateinit var questionObserver: Observer<BreedQuestion>
    @Mock
    private lateinit var loadingObserver: Observer<Boolean>
    @Mock
    private lateinit var errorObserver: Observer<Boolean>
    @Mock
    private lateinit var celebrationObserver: Observer<Boolean>
    @Mock
    private lateinit var readyForNextQuestionObserver: Observer<Boolean>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(useCase, TestContextProvider(), Configuration(2)).apply {
            currentQuestion.observeForever(questionObserver)
            isLoading.observeForever(loadingObserver)
            loadingError.observeForever(errorObserver)
            showCelebration.observeForever(celebrationObserver)
            readyForNextQuestion.observeForever(readyForNextQuestionObserver)
        }
    }

    @Test
    fun `should have data when getRandomDog returns proper data`() {
        testCoroutineRule.runBlockingTest {
            val data = BreedQuestion("name", listOf(Option("")))
            whenever(useCase.getBreedQuestion()).thenReturn(data)

            //when
            viewModel.getRandomDog()

            //then
            verify(loadingObserver).onChanged(true)
            verify(questionObserver).onChanged(data)
        }

    }

    @Test
    fun `should fail when getRandomDog throws error`() {
        testCoroutineRule.runBlockingTest {
            val error = Error()
            whenever(useCase.getBreedQuestion()).thenThrow(error)

            //when
            viewModel.getRandomDog()

            //then
            verify(loadingObserver).onChanged(true)
            verify(errorObserver).onChanged(true)
        }
    }

    @Test
    fun `should set correct UI state when correct option is selected`(){
        val option = Option("Breed name", isCorrect = true, isSelected = true)
        viewModel.selectOption(option)

        verify(readyForNextQuestionObserver).onChanged(true) //verify that readyForNextQuestionObserver receives true update
        verify(celebrationObserver, times(1)).onChanged(true) //verify that observer received update just once
        verify(questionObserver, times(2)) //verify that question observer received update too

    }

    @Test
    fun `should set correct UI state when incorrect option is selected`(){
        val option = Option("Breed name", isCorrect = false, isSelected = true)
        viewModel.selectOption(option)

        verifyZeroInteractions(readyForNextQuestionObserver)
        verifyZeroInteractions(celebrationObserver) //verify no update to celebration observer
        verify(questionObserver, times(1)) //verify that question observer received update


    }
}