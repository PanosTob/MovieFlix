package com.panostob.movieflix.framework.movies.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.panostob.movieflix.data.movies.model.local.LocalFavoriteMovie
import com.panostob.movieflix.framework.movies.datasource.local.dao.MovieDao

@Database(
    entities = [
        LocalFavoriteMovie::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}