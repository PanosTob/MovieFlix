package com.panostob.movieflix.ui.home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.panostob.movieflix.ui.home.model.HomeUiState
import com.panostob.movieflix.util.composable.AnimatedNoInternetConnectionItem
import com.panostob.movieflix.util.navigation.NavigationRoute

@Composable
fun HomeScreen(
    uiState: State<HomeUiState>,
    navigateToRoute: (NavigationRoute) -> Unit,
) {
    HomeContent(
        uiState = uiState.value,
        navigateToRoute = navigateToRoute,
    )

    AnimatedNoInternetConnectionItem(
        showInternetDialog = uiState.value.showNoInternetConnectionView,
        onDismissConnectionView = { uiState.value.onDismissConnectionView() }
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    navigateToRoute: (NavigationRoute) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
    }
}