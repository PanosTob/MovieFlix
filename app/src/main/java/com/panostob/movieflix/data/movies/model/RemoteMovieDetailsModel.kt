package com.panostob.movieflix.data.movies.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovieDetailsResponse(
    val id: Int?,
    val title: String?,
    @Json(name = "backdrop_path") val imagePath: String?,
    val genres: List<RemoteMovieGenreItem>?,
    @Json(name = "release_date") val date: String?,
    @Json(name = "vote_average") val starRating: Double?,
    val runtime: Int?,
    @Json(name = "overview") val description: String?,
    val credits: RemoteMovieCreditsResponse?,
    val reviews: RemoteMovieReviewsResponse?,
    val similar: RemoteSimilarMoviesResponse?
)

@JsonClass(generateAdapter = true)
data class RemoteMovieGenreItem(
    val id: Int?,
    val name: String?
)