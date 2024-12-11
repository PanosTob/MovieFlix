package com.panostob.movieflix.data.movies.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovieReviewsResponse(
    val results: List<RemoteMovieReviewItem>?
)

@JsonClass(generateAdapter = true)
data class RemoteMovieReviewItem(
    val id: Int?,
    val content: String?,
    val reviewUrl: String?,
    @Json(name = "author_details") val authorDetails: RemoteMovieAuthorItem?
)

@JsonClass(generateAdapter = true)
data class RemoteMovieAuthorItem(
    val name: String?,
    val username: String?,
    @Json(name = "avatar_path") val authorImage: String?,
    val rating: Double?
)

