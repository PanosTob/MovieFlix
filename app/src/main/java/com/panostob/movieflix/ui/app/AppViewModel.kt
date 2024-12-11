package com.panostob.movieflix.ui.app

import androidx.lifecycle.SavedStateHandle
import com.panostob.movieflix.ui.app.model.AppUiState
import com.panostob.movieflix.ui.base.BaseViewModel
import com.panostob.movieflix.util.delegate.SaveStateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import com.panostob.movieflix.util.pod.PoDHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val podHelper: PoDHelper,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = _uiState.asStateFlow()

    private var isAlreadyCreated by SaveStateDelegate<Boolean>(savedStateHandle = savedStateHandle, key = SAVE_STATE_APP)

    init {
        initPoD()
    }

    private fun initPoD() {
        // catch PoD case - restart app
        val processOfDeathCase = isAlreadyCreated == true && !podHelper.isAlreadyCreated
        if (processOfDeathCase) {
            _uiState.value.restartApplication.value = Unit
        }

        isAlreadyCreated = true
        podHelper.initPoDHelper()
    }

    fun handleLoading(isLoading: Boolean) {
        _uiState.value.loadingUiState.isLoading.value = isLoading
    }

    companion object {
        private const val SAVE_STATE_APP = "SAVE_STATE_APP"
    }
}