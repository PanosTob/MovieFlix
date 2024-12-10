package com.panostob.movieflix.ui.app.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.panostob.movieflix.ui.app.model.AppLoadingUiItem
import com.panostob.movieflix.util.composable.noRippleClickable

@Composable
fun AppProgressIndicator(
    uiState: AppLoadingUiItem,
    modifier: Modifier = Modifier
) {
    if (uiState.isLoading.value) {
        Box(
            modifier = modifier
                .background(color = uiState.backgroundColor.value)
                .noRippleClickable { },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = uiState.indicatorColor.value
            )
        }
    }
}