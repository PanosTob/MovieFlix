package com.panostob.movieflix.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panostob.movieflix.util.livedata.LoadingLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    protected fun launch(function: suspend () -> Unit): Job {
        return viewModelScope.launch {
            function.invoke()
        }
    }

    protected fun launchOnIO(function: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                function()
            }
        }
    }

    protected fun launchWithProgress(
        preload: suspend () -> Unit = {},
        postload: () -> Unit = {},
        function: suspend () -> Unit
    ) {
        viewModelScope.launch {
            preload.invoke()
            LoadingLiveData.postValue(true)
            function.invoke()
        }.apply {
            invokeOnCompletion {
                postload.invoke()
                LoadingLiveData.postValue(false)
            }
        }
    }
}