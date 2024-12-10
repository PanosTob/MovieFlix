package com.panostob.movieflix.util.network.connectivity

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Wifi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Cellular

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Internet