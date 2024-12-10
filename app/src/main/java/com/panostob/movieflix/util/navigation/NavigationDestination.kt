package com.panostob.movieflix.util.navigation

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.reflect.KClass

interface NavigationDestination

@Serializable
open class NavigationRoute {

    @Transient var popUpToKlass: KClass<out NavigationRoute>? = null
    @Transient var inclusive: Boolean = false
    @Transient var launchSingleTop: Boolean = true
    @Transient var restoreState: Boolean = false
    @Transient var saveState: Boolean = false

    fun inclusive(kClass: KClass<out NavigationRoute>) {
        popUpToKlass = kClass
        inclusive = true
    }
}