package com.yomi.doggo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yomi.doggo.network.model.Dog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class HomeViewModel(private val usecase: RandomDogUseCase) : ViewModel() {

    private val _currentDog = MutableLiveData<Dog>()
    val currentDog: LiveData<Dog> = _currentDog

    private val errorHandler = CoroutineExceptionHandler{_, exception -> handleError(exception)}

    fun getRandomDog() {
        viewModelScope.launch(errorHandler) {
            usecase.getRandomDog(getRandomeBreed()).let {
                _currentDog.value = it
            }
        }
    }
    private fun handleError(exception: Throwable) {
        //handle error
    }

    private fun getRandomeBreed(): String {
        return "affenpinscher"
    }
}