package com.panostob.movieflix.ui.splash.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.panostob.movieflix.util.navigation.NavigationRoute

data class SplashUiState(
    val navigateToRoute: MutableState<NavigationRoute?> = mutableStateOf(null)
)