package com.yomi.doggo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yomi.doggo.ui.model.BreedQuestion
import com.yomi.doggo.ui.model.Option
import com.yomi.doggo.util.Constants
import com.yomi.doggo.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class HomeViewModel(private val usecase: BreedsUseCase) : ViewModel() {

    private val _currentQuestion = MutableLiveData<BreedQuestion>()
    val currentQuestion: LiveData<BreedQuestion> = _currentQuestion

    private val _showCelebration = SingleLiveEvent<Boolean>()
    val showCelebration = _showCelebration

    private val _readyForNextQuestion = SingleLiveEvent<Boolean>()
    val readyForNextQuestion = _readyForNextQuestion

    private val _resetView = SingleLiveEvent<Boolean>()
    val resetView = _resetView

    var numberOfTries = 0

        private val errorHandler = CoroutineExceptionHandler{_, exception -> handleError(exception)}

    fun getRandomDog() {
        reset()
        viewModelScope.launch(errorHandler) {
            usecase.getRandomDog().let {
                _currentQuestion.value = it
            }
        }
    }
    private fun handleError(exception: Throwable) {
        //handle error
    }

    fun onOptionSelected(option: Option) {
        numberOfTries ++
        if (numberOfTries == Constants.NUMBER_OF_TRIES){
            _readyForNextQuestion.postValue(true)
        } else {
            setCorrectAnswerState(option.isCorrect)
        }
        _currentQuestion.value?.options?.find { it.name == option.name }?.isSelected = true
        _currentQuestion.value = _currentQuestion.value //force postValue to notify observers
    }

    private fun reset() {
        _resetView.postValue(true)
        _readyForNextQuestion.postValue(false)
        _showCelebration.postValue(false)
        numberOfTries = 0
    }

    private fun setCorrectAnswerState(isCorrect: Boolean) {
        _showCelebration.postValue(isCorrect)
        _readyForNextQuestion.postValue(isCorrect)
    }
}