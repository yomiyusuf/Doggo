package com.yomi.doggo.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * Created by Yomi Joseph on 2020-07-22.
 *
 * Superclass for viewmodeld  with common methods and livedata
 */
open class ProgressViewModel: ViewModel() {

    protected val errorHandler = CoroutineExceptionHandler{_, exception -> handleError(exception)}

    private val _loading = MutableLiveData<Boolean>()
    val isLoading = _loading

    private val _loadingError = MutableLiveData<Boolean>()
    val loadingError = _loadingError

    protected fun transitionToBusy() {
        _loading.postValue(true)
    }

    protected fun transitionToIdle() {
        _loading.postValue(false)
    }

    private fun handleError(exception: Throwable) {
        transitionToIdle()
        _loadingError.value = true
    }

    protected open fun reset() {
        _loadingError.value = false
    }
}