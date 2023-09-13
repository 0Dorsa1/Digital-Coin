package com.example.digitalcoin.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalcoin.model.GetCoinDetailResponse
import com.example.digitalcoin.ui.repository.CoinDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsDetailViewModel @Inject constructor(private val repository: CoinDetailRepository) :
    ViewModel() {
    var state by mutableStateOf(ScreenState())

    fun call(id: String) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isError = false
            )

            delay(2000)

            repository.call(
                id = id,
            )
                .catch { exception ->
                    state = state.copy(
                        isLoading = false,
                        isError = true,
                        exception = exception.toString()
                    )
                }
                .collectLatest { response ->
                    state = state.copy(
                        response = response,
                        isLoading = false,
                        isError = false
                    )
                }
        }
    }

    data class ScreenState(
        var isLoading: Boolean = false,
        var isError: Boolean = false,
        var response: GetCoinDetailResponse? = null,
        var exception: String? = null
    )
}
