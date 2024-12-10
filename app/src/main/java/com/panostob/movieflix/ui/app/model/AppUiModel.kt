package com.panostob.movieflix.ui.app.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

data class AppUiState(
    val loadingUiState: AppLoadingUiItem = AppLoadingUiItem(
        isLoading = mutableStateOf(false),
        backgroundColor = mutableStateOf(Color.Black),
        indicatorColor = mutableStateOf(Color.White),
    ),
    val restartApplication: MutableState<Unit?> = mutableStateOf(null),
)

data class AppLoadingUiItem(
    val isLoading: MutableState<Boolean>,
    val backgroundColor: MutableState<Color>,
    val indicatorColor: MutableState<Color>
)