package com.panostob.movieflix.data.movies.mapper

import com.panostob.movieflix.BuildConfig
import com.panostob.movieflix.data.movies.model.remote.RemoteMovieAuthorItem
import com.panostob.movieflix.data.movies.model.remote.RemoteMovieReviewsResponse
import com.panostob.movieflix.domain.movies.entity.MovieAuthor
import com.panostob.movieflix.domain.movies.entity.MovieReview
import javax.inject.Inject

class MovieReviewsMapper @Inject constructor() {
    operator fun invoke(response: RemoteMovieReviewsResponse): List<MovieReview> {
        if (response.results.isNullOrEmpty()) return emptyList()

        return response.results.mapNotNull { review ->
            if (review.id == null) return@mapNotNull null

            MovieReview(
                id = review.id,
                content = review.content ?: "",
                url = review.reviewUrl ?: "",
                authorDetails = mapAuthorDetails(review.authorDetails),
                rating = review.authorDetails?.rating
            )
        }
    }

    private fun mapAuthorDetails(remoteAuthorDetails: RemoteMovieAuthorItem?): MovieAuthor {
        return MovieAuthor(
            name = remoteAuthorDetails?.name ?: "",
            username = remoteAuthorDetails?.username ?: "",
            authorImage = BuildConfig.TMDB_JSON_API_IMAGE_URL + remoteAuthorDetails?.authorImage,

            )
    }
}