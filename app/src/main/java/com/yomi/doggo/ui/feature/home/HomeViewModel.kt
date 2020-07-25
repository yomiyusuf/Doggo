package com.yomi.doggo.ui.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yomi.doggo.ui.common.CoroutineContextProvider
import com.yomi.doggo.ui.common.ProgressViewModel
import com.yomi.doggo.ui.model.BreedQuestion
import com.yomi.doggo.ui.model.Option
import com.yomi.doggo.util.Configuration
import com.yomi.doggo.util.SingleLiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Yomi Joseph on 2020-07-21.
 */
class HomeViewModel(
    private val useCase: BreedQuestionUseCase,
    private val contextProvider: CoroutineContextProvider,
    private val config: Configuration
) : ProgressViewModel() {

    private val _currentQuestion = MutableLiveData<BreedQuestion>()
    val currentQuestion: LiveData<BreedQuestion> = _currentQuestion

    private val _showCelebration = SingleLiveEvent<Boolean>()
    val showCelebration : LiveData<Boolean> = _showCelebration

    private val _readyForNextQuestion = MutableLiveData<Boolean>()
    val readyForNextQuestion : LiveData<Boolean> = _readyForNextQuestion

    private val _resetView = SingleLiveEvent<Boolean>()
    val resetView : LiveData<Boolean> = _resetView

    private val _chancesLeft = MutableLiveData<Pair<Boolean, Int?>>()
    val chancesLeft : LiveData<Pair<Boolean, Int?>> = _chancesLeft

    var numberOfChancesLeft = config.NUMBER_OF_CHANCES


    fun getRandomDog() {
        reset(config.NUMBER_OF_CHANCES)
        transitionToBusy()
        viewModelScope.launch(errorHandler) {
            val question = withContext(contextProvider.IO) {
                useCase.getBreedQuestion()
            }
            _currentQuestion.value = question
            transitionToIdle()
        }
    }

    fun selectOption(option: Option) {
        updateCurrentQuestion(option)
        if (option.isCorrect) onCorrectOptionSelected() else onWrongOptionSelected()
    }

    private fun updateCurrentQuestion(option: Option) {
        _currentQuestion.value?.options?.find { it.name == option.name }?.isSelected = true
        _currentQuestion.value = _currentQuestion.value //force postValue to notify observers
    }

    private fun onCorrectOptionSelected() {
        _readyForNextQuestion.postValue(true)
        _showCelebration.postValue(true)
        _chancesLeft.postValue(Pair(false, null))
    }

    private fun onWrongOptionSelected() {
        numberOfChancesLeft--
        if (numberOfChancesLeft == 0) _readyForNextQuestion.postValue(true)
        _chancesLeft.postValue(Pair(true, numberOfChancesLeft))
    }

    private fun reset(numberOfChances: Int) {
        reset()
        _resetView.postValue(true)
        _readyForNextQuestion.postValue(false)
        _showCelebration.postValue(false)
        _chancesLeft.postValue(Pair(false, null))
        this.numberOfChancesLeft = numberOfChances
    }
}