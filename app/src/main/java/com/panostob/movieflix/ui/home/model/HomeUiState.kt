package com.panostob.movieflix.ui.home.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.panostob.movieflix.util.navigation.NavigationRoute

data class HomeUiState(
    val navigateToRoute: MutableState<NavigationRoute?> = mutableStateOf(null),
    val showNoInternetConnectionView: MutableState<Boolean> = mutableStateOf(false),
    val onDismissConnectionView: () -> Unit,
)