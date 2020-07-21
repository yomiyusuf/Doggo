package com.yomi.doggo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yomi.doggo.ui.common.ProgressViewModel
import com.yomi.doggo.ui.model.BreedQuestion
import com.yomi.doggo.ui.model.Option
import com.yomi.doggo.util.Constants
import com.yomi.doggo.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * Created by Yomi Joseph on 2020-07-21.
 */
class HomeViewModel(private val useCase: BreedQuestionUseCase) : ProgressViewModel() {

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

    var numberOfChances = Constants.NUMBER_OF_CHANCES


    fun getRandomDog() {
        reset(Constants.NUMBER_OF_CHANCES)
        transitionToBusy()
        viewModelScope.launch(errorHandler) {
            useCase.getRandomDog().let {
                _currentQuestion.value = it
            }
            transitionToIdle()
        }

    }
    override fun handleError(exception: Throwable) {
        transitionToIdle()
        //handle error
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
        numberOfChances--
        if (numberOfChances == 0) _readyForNextQuestion.postValue(true)
        _chancesLeft.postValue(Pair(true, numberOfChances))
    }

    private fun reset(numberOfChances: Int) {
        _resetView.postValue(true)
        _readyForNextQuestion.postValue(false)
        _showCelebration.postValue(false)
        _chancesLeft.postValue(Pair(false, null))
        this.numberOfChances = numberOfChances
    }
}