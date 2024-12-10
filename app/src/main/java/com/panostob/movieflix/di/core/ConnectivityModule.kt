package com.panostob.movieflix.di.core

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.panostob.movieflix.util.network.connectivity.Cellular
import com.panostob.movieflix.util.network.connectivity.CellularConnection
import com.panostob.movieflix.util.network.connectivity.Connection
import com.panostob.movieflix.util.network.connectivity.Internet
import com.panostob.movieflix.util.network.connectivity.InternetConnection
import com.panostob.movieflix.util.network.connectivity.Wifi
import com.panostob.movieflix.util.network.connectivity.WifiConnection
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConnectivityModule {

    @Wifi
    @Singleton
    @Provides
    fun provideWifiConnection(@ApplicationContext context: Context): Connection {
        return WifiConnection(context)
    }

    @Cellular
    @Singleton
    @Provides
    fun provideCellularConnection(@ApplicationContext context: Context): Connection {
        return CellularConnection(context)
    }

    @Internet
    @Singleton
    @Provides
    fun provideInternetConnection(@Wifi wifiConnection: Connection, @Cellular cellularConnection: Connection): Connection {
        return InternetConnection(wifiConnection, cellularConnection)
    }

    @Provides
    fun provideConnectivityManager(application: Application): ConnectivityManager {
        return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}