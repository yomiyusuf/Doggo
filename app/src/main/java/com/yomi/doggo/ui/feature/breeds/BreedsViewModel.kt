package com.yomi.doggo.ui.feature.breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yomi.doggo.ui.common.CoroutineContextProvider
import com.yomi.doggo.ui.common.ProgressViewModel
import com.yomi.doggo.ui.model.BreedDetail
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreedsViewModel(
    private val useCase: BreedsUseCase,
    private val contextProvider: CoroutineContextProvider
) : ProgressViewModel() {

    private val _breeds = MutableLiveData<List<BreedDetail>>()
    val breeds: LiveData<List<BreedDetail>> = _breeds

    fun getBreeds() {
        reset()
        transitionToBusy()
        viewModelScope.launch(errorHandler) {
            val data = withContext(contextProvider.IO) {
                useCase.getSampleBreeds()
            }
            _breeds.value = data
        }
    }
}