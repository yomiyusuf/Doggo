package com.yomi.doggo.ui.breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yomi.doggo.ui.common.ProgressViewModel
import com.yomi.doggo.ui.model.BreedDetail
import kotlinx.coroutines.launch

class BreedsViewModel(private val useCase: BreedsUseCase) : ProgressViewModel() {

    private val _breeds = MutableLiveData<List<BreedDetail>>()
    val breeds: LiveData<List<BreedDetail>> = _breeds

    fun getBreeds() {
        reset()
        transitionToBusy()
        viewModelScope.launch(errorHandler) {
            useCase.getSampleBreeds().let {
                _breeds.value = it
            }
        }
    }
}