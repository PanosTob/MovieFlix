package com.panostob.movieflix.util.network.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun rememberConnectivityMonitor(
    onConnected: () -> Unit,
    onDisconnected: () -> Unit,
): ConnectivityLifecycleObserver {

    val context = LocalContext.current
    val callback = remember {
        object : OnConnectivityChangeCallback {
            override fun onConnected() {
                onConnected()
            }

            override fun onDisconnected() {
                onDisconnected()
            }
        }
    }
    val connectivityLifecycleObserver = remember { ConnectivityLifecycleObserver(context = context, callback = callback) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(connectivityLifecycleObserver) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> connectivityLifecycleObserver.subscribe()
                Lifecycle.Event.ON_STOP -> connectivityLifecycleObserver.unsubscribe()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            connectivityLifecycleObserver.unsubscribe()
        }
    }

    return connectivityLifecycleObserver
}

class ConnectivityLifecycleObserver(
    context: Context,
    callback: OnConnectivityChangeCallback
) : OnConnectivityChangeObserver {

    private var connectivityManager: ConnectivityManager? = null

    init {
        connectivityManager = ContextCompat.getSystemService(context, ConnectivityManager::class.java)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            callback.onConnected()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            callback.onDisconnected()
        }
    }

    override fun subscribe() {
        connectivityManager?.registerDefaultNetworkCallback(networkCallback)
    }

    override fun unsubscribe() {
        try {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        } catch (ex: Exception) {
            Log.e(ConnectivityLifecycleObserver::class.simpleName.toString(), ex.toString())
        }
    }
}

interface OnConnectivityChangeObserver {

    fun subscribe()

    fun unsubscribe()
}

interface OnConnectivityChangeCallback {

    fun onConnected()

    fun onDisconnected()
}