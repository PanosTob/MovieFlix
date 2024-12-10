package com.panostob.movieflix.ui.app.navigation

import androidx.compose.runtime.State
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.panostob.movieflix.ui.home.navigation.homeScreen
import com.panostob.movieflix.ui.splash.navigation.SplashRoute
import com.panostob.movieflix.ui.splash.navigation.splashScreen
import com.panostob.movieflix.util.ext.safeNavigate
import com.panostob.movieflix.util.navigation.NavigationDestination
import com.panostob.movieflix.util.navigation.NavigationRoute
import kotlinx.serialization.Serializable

@Serializable
data object AppNavGraphRoute : NavigationDestination

internal fun NavGraphBuilder.appNavGraph(
    navController: NavHostController
) {
    navigation<AppNavGraphRoute>(
        startDestination = SplashRoute
    ) {
        splashScreen(
            navigateToRoute = { route -> navController.safeNavigate(route) }
        )
        homeScreen(
            navigateToRoute = { route -> navController.safeNavigate(route) }
        )
    }
}