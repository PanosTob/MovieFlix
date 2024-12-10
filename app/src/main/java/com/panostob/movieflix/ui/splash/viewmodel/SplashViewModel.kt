package com.panostob.movieflix.ui.splash.viewmodel

import android.R.attr.version
import com.panostob.movieflix.BuildConfig
import com.panostob.movieflix.ui.base.BaseViewModel
import com.panostob.movieflix.ui.home.navigation.HomeRoute
import com.panostob.movieflix.ui.splash.model.SplashUiState
import com.panostob.movieflix.ui.splash.navigation.SplashRoute
import com.panostob.movieflix.util.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState = _uiState.asStateFlow()

    init {
        launch {
            delay(1000)
            navigateToRoute(HomeRoute.apply { inclusive(SplashRoute::class) })
        }
    }

    fun navigateToRoute(route: NavigationRoute) {
        _uiState.value.navigateToRoute.value = route
    }
}