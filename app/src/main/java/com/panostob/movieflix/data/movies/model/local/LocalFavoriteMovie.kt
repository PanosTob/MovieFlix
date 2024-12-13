package com.panostob.movieflix.data.movies.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_movies")
data class LocalFavoriteMovie(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("movie_id")  val movieId: Int
)