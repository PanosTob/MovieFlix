package com.panostob.movieflix.data.movies.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovieCreditsResponse(
    val cast: List<RemoteMovieCastItem>?
)

@JsonClass(generateAdapter = true)
data class RemoteMovieCastItem(
    val id: Int?,
    val name: String?,
    @Json(name = "profile_path") val imagePath: String?,
    val character: String?,
    val order: Int?
)