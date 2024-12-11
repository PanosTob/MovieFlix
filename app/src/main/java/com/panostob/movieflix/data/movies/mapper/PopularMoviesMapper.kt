package com.panostob.movieflix.data.movies.mapper

import com.panostob.movieflix.BuildConfig
import com.panostob.movieflix.data.movies.model.RemotePopularMoviesResponse
import com.panostob.movieflix.domain.movies.entity.PopularMovie
import javax.inject.Inject

class PopularMoviesMapper @Inject constructor() {

    operator fun invoke(response: RemotePopularMoviesResponse): List<PopularMovie> {
        if (response.movies.isNullOrEmpty()) return emptyList()

        return response.movies.mapNotNull { remoteMovie ->
            if (remoteMovie.id == null) return@mapNotNull null
            PopularMovie(
                id = remoteMovie.id,
                title = remoteMovie.title ?: "",
                imagePath = BuildConfig.TMDB_JSON_API_IMAGE_URL + remoteMovie.imagePath,
                date = remoteMovie.date ?: "",
                starRating = remoteMovie.starRating?.times(5)?.div(10)
            )
        }
    }
}