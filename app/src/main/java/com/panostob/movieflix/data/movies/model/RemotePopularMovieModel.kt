package com.panostob.movieflix.data.movies.model

import android.R.attr.name
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemotePopularMoviesResponse(
    @Json(name = "results") val movies: List<RemotePopularMovieItem>?
)

@JsonClass(generateAdapter = true)
data class RemotePopularMovieItem(
    val id: Int?,
    val title: String?,
    @Json(name = "backdrop_path") val imagePath: String?,
    @Json(name = "release_date") val date: String?,
    @Json(name = "vote_average") val starRating: Double?
)