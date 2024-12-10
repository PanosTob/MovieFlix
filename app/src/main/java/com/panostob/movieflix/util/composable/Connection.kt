package com.panostob.movieflix.util.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
fun AnimatedNoInternetConnectionItem(
    showInternetDialog: State<Boolean>,
    onDismissConnectionView: () -> Unit,
) {
    AnimatedVisibility(
        visible = showInternetDialog.value,
        enter = fadeIn(animationSpec = tween(durationMillis = 400)),
        exit = fadeOut(animationSpec = tween(durationMillis = 400))
    ) {
        //TODO ADD SNACKBAR
    }
}