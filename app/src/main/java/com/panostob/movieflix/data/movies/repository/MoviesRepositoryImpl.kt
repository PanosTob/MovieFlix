package com.panostob.movieflix.data.movies.repository

import com.panostob.movieflix.data.movies.datasource.MoviesDataSource
import com.panostob.movieflix.data.movies.mapper.MovieDetailsMapper
import com.panostob.movieflix.data.movies.mapper.MovieReviewsMapper
import com.panostob.movieflix.data.movies.mapper.PopularMoviesMapper
import com.panostob.movieflix.data.movies.mapper.SimilarMovieMapper
import com.panostob.movieflix.domain.movies.entity.MovieDetails
import com.panostob.movieflix.domain.movies.entity.MovieReview
import com.panostob.movieflix.domain.movies.entity.PopularMovie
import com.panostob.movieflix.domain.movies.entity.SimilarMovie
import com.panostob.movieflix.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val dataSource: MoviesDataSource,
    private val popularMoviesMapper: PopularMoviesMapper,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val movieReviewsMapper: MovieReviewsMapper,
    private val similarMovieMapper: SimilarMovieMapper
): MoviesRepository {
    override suspend fun getPopularMovies(page: Int): List<PopularMovie> {
        return popularMoviesMapper(dataSource.getPopularMovies(page))
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails? {
        return movieDetailsMapper(dataSource.getMovieDetails(movieId))
    }

    override suspend fun getMovieReviews(movieId: Int): List<MovieReview>? {
        return movieReviewsMapper(dataSource.getMovieReviews(movieId))
    }

    override suspend fun getSimilarMovies(movieId: Int): List<SimilarMovie>? {
        return similarMovieMapper(dataSource.getSimilarMovies(movieId))
    }
}