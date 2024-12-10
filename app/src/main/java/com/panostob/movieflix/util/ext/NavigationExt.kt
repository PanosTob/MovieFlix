package com.panostob.movieflix.util.ext

import android.util.Log
import androidx.navigation.NavController
import com.panostob.movieflix.util.navigation.NavigationRoute

internal fun NavController.safeNavigate(
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
