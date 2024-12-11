package com.panostob.movieflix.data.movies.mapper

import com.panostob.movieflix.BuildConfig
import com.panostob.movieflix.data.movies.model.RemoteMovieDetailsResponse
import com.panostob.movieflix.data.movies.model.RemoteMovieGenreItem
import com.panostob.movieflix.domain.movies.entity.MovieDetails
import com.panostob.movieflix.domain.movies.entity.MovieGenre
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor(
    private val movieCastMapper: MovieCastMapper
) {
    operator fun invoke(response: RemoteMovieDetailsResponse): MovieDetails? {
        if (response.id == null) return null
        return MovieDetails(
            id = response.id,
            title = response.title ?: "",
            imagePath = BuildConfig.TMDB_JSON_API_IMAGE_URL + response.imagePath,
            genres = mapGenre(response.genres),
            releaseDate = response.date ?: "",
            starRating = response.starRating?.times(5)?.div(10),
            runtime = response.runtime ?: 0,
            description = response.description ?: "",
            cast = movieCastMapper(response.credits?.cast),
        )
    }

    private fun mapGenre(remoteGenres: List<RemoteMovieGenreItem>?): List<MovieGenre> {
        if (remoteGenres.isNullOrEmpty()) return emptyList()
        return remoteGenres.mapNotNull { remoteGenre ->
            if (remoteGenre.id == null) return@mapNotNull null
            MovieGenre(
                id = remoteGenre.id,
                name = remoteGenre.name ?: ""
            )
        }
    }
}