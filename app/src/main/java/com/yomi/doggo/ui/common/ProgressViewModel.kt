package com.yomi.doggo.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Yomi Joseph on 2020-07-22.
 */
open class ProgressViewModel: ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val isLoading = _loading

    protected fun transitionToBusy() {
        _loading.postValue(true)
    }

    protected fun transitionToIdle() {
        _loading.postValue(false)
    }
}