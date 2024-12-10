package com.panostob.movieflix.util.network.connectivity

import javax.inject.Inject

class InternetConnection @Inject constructor(
    @Wifi val wifiConnection: Connection,
    @Cellular val cellularConnection: Connection,
) : Connection {

    override fun isConnected(): Boolean {
        return wifiConnection.isConnected() || cellularConnection.isConnected()
    }
}