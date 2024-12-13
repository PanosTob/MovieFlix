package com.panostob.movieflix.util.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.panostob.movieflix.R

@Composable
fun NoInternetConnectionItem(
    showInternetDialog: State<Boolean>
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val noConnectionString = stringResource(id = R.string.no_internet_connection_text)
    LaunchedEffect(showInternetDialog.value) {
        if (showInternetDialog.value) {
            snackbarHostState.showSnackbar(message = noConnectionString, duration = SnackbarDuration.Indefinite)
        }
    }

    SnackbarHost(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(Alignment.Bottom),
        hostState = snackbarHostState
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(SpacingQuarter_4dp)
                    .fillMaxWidth()
                    .padding(vertical = SpacingDouble_32dp)
                    .graphicsLayer {
                        shadowElevation = 5f
                    }
                    .background(color = Color.Black)
                    .padding(vertical = SpacingHalf_8dp)
                    .align(Alignment.Center),
                text = stringResource(id = R.string.no_internet_connection_text),
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}