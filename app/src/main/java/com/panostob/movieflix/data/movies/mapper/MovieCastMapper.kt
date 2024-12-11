package com.panostob.movieflix.data.movies.mapper

import android.R.attr.order
import com.panostob.movieflix.BuildConfig
import com.panostob.movieflix.data.movies.model.RemoteMovieCastItem
import com.panostob.movieflix.domain.movies.entity.MovieCastItem
import javax.inject.Inject

class MovieCastMapper @Inject constructor() {

    operator fun invoke(response: List<RemoteMovieCastItem>?): List<MovieCastItem> {
        if (response.isNullOrEmpty()) return emptyList()
        return response.mapNotNull { castItem ->
            if (castItem.id == null) return@mapNotNull null
            MovieCastItem(
                id = castItem.id,
                name = castItem.name ?: "",
                imagePath = BuildConfig.TMDB_JSON_API_IMAGE_URL + castItem.imagePath,
                character = castItem.character ?: "",
                order = castItem.order ?: Int.MAX_VALUE
            )
        }
    }
}