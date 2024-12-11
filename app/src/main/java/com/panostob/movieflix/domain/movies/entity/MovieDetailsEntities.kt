package com.panostob.movieflix.domain.movies.entity

data class MovieDetails(
    val id: Int,
    val title: String,
    val imagePath: String,
    val genres: List<MovieGenre>,
    val releaseDate: String,
    val starRating: Double?,
    val runtime: Int?,
    val description: String,
    val cast: List<MovieCastItem>,
    val reviews: MutableList<MovieReview> = mutableListOf(),
    val similarMovies: MutableList<SimilarMovie> = mutableListOf()
)

data class MovieGenre(
    val id: Int,
    val name: String
)

data class MovieCastItem(
    val id: Int,
    val name: String,
    val imagePath: String,
    val character: String,
    val order: Int
)

data class MovieReview(
    val id: Int,
    val content: String,
    val url: String,
    val authorDetails: MovieAuthor,
    val rating: Double?
)

data class MovieAuthor(
    val name: String,
    val username: String,
    val authorImage: String
)

data class SimilarMovie(
    val id: Int,
    val imagePath: String,
    val title: String
)