package com.panostob.movieflix.util.network.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class CellularConnection(private val context: Context) : Connection {

    override fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkInfo?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
    }
}
