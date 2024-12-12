package com.panostob.movieflix.util.ext

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.panostob.movieflix.util.navigation.NavigationRoute
import kotlin.reflect.KType

fun NavController.safeNavigate(
    route: NavigationRoute,
) {
    try {
        navigate(route) {
            launchSingleTop = route.launchSingleTop
            restoreState = route.restoreState
            route.popUpToKlass?.let { kClass ->
                popUpTo(klass = kClass) {
                    inclusive = route.inclusive
                    saveState = route.saveState
                }
            }
        }
        Log.v("safeNavigate", route.toString())
    } catch (e: Exception) {
        Log.e("safeNavigate", e.toString())
    }
}

inline fun <reified T : Any> NavGraphBuilder.slideInOutComposable(
    typeMap: Map<KType, NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline sizeTransform: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? = null,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        typeMap = typeMap,
        deepLinks = deepLinks,
        sizeTransform = sizeTransform,
        enterTransition = { slideInHorizontally { offset -> offset } },
        popExitTransition = { slideOutHorizontally { offset -> offset } },
        exitTransition = { slideOutHorizontally { offset -> -offset } },
        popEnterTransition = { slideInHorizontally { offset -> -offset } },
        content = content
    )
}
