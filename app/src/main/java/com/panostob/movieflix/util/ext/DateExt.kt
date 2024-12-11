package com.panostob.movieflix.util.ext

import com.panostob.movieflix.util.TMDB_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.Locale

fun Long.toDate(format: String): String {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).format(this)
    } catch (e: Exception) {
        ""
    }
}

fun String.dateToTimestamp(sourcePattern: String = TMDB_DATE_FORMAT): Long? {
    return try {
        SimpleDateFormat(sourcePattern, Locale.getDefault()).parse(this)?.time
    } catch (ex: Exception) {
        null
    }
}