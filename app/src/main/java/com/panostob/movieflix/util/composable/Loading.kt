package com.panostob.movieflix.util.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingProgress(
    modifier: Modifier = Modifier,
    loaderSize: Dp = SpacingQuadruple_64dp
) {
    Column(modifier = modifier.wrapContentSize()) {
        CircularProgressIndicator(
            modifier = Modifier.size(loaderSize),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = SpacingEighth_2dp,
        )
    }
}