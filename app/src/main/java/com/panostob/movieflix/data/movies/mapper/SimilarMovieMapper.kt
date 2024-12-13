package com.panostob.movieflix.data.movies.mapper

import com.panostob.movieflix.BuildConfig
import com.panostob.movieflix.data.movies.model.remote.RemoteSimilarMoviesResponse
import com.panostob.movieflix.domain.movies.entity.SimilarMovie
import javax.inject.Inject

class SimilarMovieMapper @Inject constructor() {

    operator fun invoke(response: RemoteSimilarMoviesResponse): List<SimilarMovie> {
        if (response.results.isNullOrEmpty()) return emptyList()
        return response.results.mapNotNull { similarMovie ->
            if (similarMovie.id == null) return@mapNotNull null
            SimilarMovie(
                id = similarMovie.id,
                title = similarMovie.title ?: "",
                imagePath = BuildConfig.TMDB_JSON_API_IMAGE_URL + similarMovie.imagePath,
            )
        }
    }
}