package com.panostob.movieflix.data.movies.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteSimilarMoviesResponse(
    val results: List<RemoteSimilarMovieItem>?
)

@JsonClass(generateAdapter = true)
data class RemoteSimilarMovieItem(
    val id: Int?,
    @Json(name = "backdrop_path") val imagePath: String?,
    val title: String?,
)