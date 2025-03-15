package com.dlucci.baseapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlucci.baseapplication.database.SampleDatabase
import com.dlucci.baseapplication.model.SampleResponse
import com.dlucci.baseapplication.repository.SampleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

sealed class SampleState {
    data class RemoteSuccess(val sample: List<SampleResponse>) : SampleState()
    data class LocalSuccess(val sample: List<SampleResponse>) : SampleState()
    data class Error(val message: String, val sample: List<SampleResponse>) : SampleState()
    data class Loading(val isLoading: Boolean) : SampleState()
}

class SampleViewModel(
    private val sampleRepository: SampleRepository,
    private val sampleDatabase: SampleDatabase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SampleState>(SampleState.Loading(false))
    val uiState: StateFlow<SampleState> = _uiState

    init {
        updateState(SampleState.Loading(true))
        viewModelScope.launch {
            val sample = sampleRepository.getNewsStories
            flow {
                sample.collect { data ->
                    if (data.isSuccess) {
                        val result = data.getOrNull()
                        if (result != null) {
                            emit(SampleState.RemoteSuccess(result))
                        } else {
                            emit(SampleState.Error("No Data", emptyList()))
                        }
                    }
                }
            }
                .catch { emit(SampleState.Error(it.message ?: "Unknown Error", emptyList())) }
                .collect({ updateState(it) })
        }
    }


    private fun updateState(state: SampleState) {
        this._uiState.value = state
    }
}