package com.yomi.doggo.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * Created by Yomi Joseph on 2020-07-22.
 */
abstract class ProgressViewModel: ViewModel() {

    protected val errorHandler = CoroutineExceptionHandler{_, exception -> handleError(exception)}

    private val _loading = MutableLiveData<Boolean>()
    val isLoading = _loading

    protected fun transitionToBusy() {
        _loading.postValue(true)
    }

    protected fun transitionToIdle() {
        _loading.postValue(false)
    }

    abstract fun handleError(exception: Throwable)
}