package com.panostob.movieflix.framework.movies

import com.panostob.movieflix.data.movies.model.RemoteMovieDetailsResponse
import com.panostob.movieflix.data.movies.model.RemoteMovieReviewsResponse
import com.panostob.movieflix.data.movies.model.RemotePopularMoviesResponse
import com.panostob.movieflix.data.movies.model.RemoteSimilarMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {

    @GET("movie/popular?language=en-US&page={page}")
    suspend fun getPopularMovies(
        @Path("page") page: Int
    ): Response<RemotePopularMoviesResponse>

    @GET("movie/{movie_id}?language=en-US&append_to_response=credits,reviews,similar")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Response<RemoteMovieDetailsResponse>

    @GET("movie/{movie_id}/similar?language=en-US&page=1")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int
    ): Response<RemoteSimilarMoviesResponse>

    @GET("movie/{movie_id}/reviews?language=en-US&page=1")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int
    ): Response<RemoteMovieReviewsResponse>
}