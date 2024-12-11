package com.panostob.movieflix.util.composable

import android.R.attr.text
import android.R.attr.textColor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panostob.movieflix.R

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
        val snackbarHostState = remember { SnackbarHostState() }
        val noConnectionString = stringResource(id = R.string.no_internet_connection_text)
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(noConnectionString)
        }

        SnackbarHost(hostState = snackbarHostState) {
            Box(
                modifier = Modifier.fillMaxSize()
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

                Button(
                    modifier = Modifier
                        .padding(top = SpacingDouble_32dp)
                        .fillMaxWidth(0.45f)
                        .aspectRatio(4f),
                    enabled = true,
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background, disabledContainerColor = MaterialTheme.colorScheme.onSurface),
                    onClick = { onDismissConnectionView() },
                    contentPadding = PaddingValues(SpacingHalf_8dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .weight(0.2f)
                                .padding(1.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = "",
                            tint = Color.Black
                        )
                        AutoSizeText(
                            modifier = Modifier
                                .weight(0.8f)
                                .padding(horizontal = SpacingQuarter_4dp),
                            text = stringResource(id = R.string.no_internet_connection_refresh_btn),
                            maxFontSize = 16.sp,
                            color = Color.Black,
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}