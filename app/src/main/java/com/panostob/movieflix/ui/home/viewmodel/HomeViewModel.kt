package com.panostob.movieflix.ui.home.viewmodel

import com.panostob.movieflix.ui.base.BaseViewModel
import com.panostob.movieflix.ui.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(
        onDismissConnectionView = { onDismissConnectionView() },
    ))
    val uiState = _uiState.asStateFlow()

    init {

    }

    private fun onDismissConnectionView() {
        _uiState.value.showNoInternetConnectionView.value = false
    }

    fun onNetworkConnected() {
        _uiState.value.showNoInternetConnectionView.value = false
    }

    fun onNetworkDisconnected() {
        _uiState.value.showNoInternetConnectionView.value = true
    }
}