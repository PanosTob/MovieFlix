package com.panostob.movieflix.ui.splash.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.panostob.movieflix.R
import com.panostob.movieflix.ui.theme.MovieFlixTheme

@Composable
fun SplashScreen() {
    SplashScreenContent()
}

@Composable
private fun SplashScreenContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TMDBAttributionLogo()
    }
}

@Composable
fun TMDBAttributionLogo(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .fillMaxWidth(0.7f)
            .aspectRatio(3f),
        painter = painterResource(id = R.drawable.tmdb_attribution_logo),
        contentDescription = ""
    )
}

@Composable
@Preview
fun SplashScreenPreview() {
    MovieFlixTheme {
        SplashScreenContent()
    }
}