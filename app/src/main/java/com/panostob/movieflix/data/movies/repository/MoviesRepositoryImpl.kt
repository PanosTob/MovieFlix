package com.panostob.movieflix.data.movies.repository

import com.panostob.movieflix.data.movies.datasource.local.MoviesLocalDataSource
import com.panostob.movieflix.data.movies.datasource.remote.MoviesRemoteDataSource
import com.panostob.movieflix.data.movies.mapper.MovieDetailsMapper
import com.panostob.movieflix.data.movies.mapper.MovieReviewsMapper
import com.panostob.movieflix.data.movies.mapper.PopularMoviesMapper
import com.panostob.movieflix.data.movies.mapper.SimilarMovieMapper
import com.panostob.movieflix.data.movies.model.local.LocalFavoriteMovie
import com.panostob.movieflix.domain.movies.entity.MovieDetails
import com.panostob.movieflix.domain.movies.entity.MovieReview
import com.panostob.movieflix.domain.movies.entity.PopularMovie
import com.panostob.movieflix.domain.movies.entity.SimilarMovie
import com.panostob.movieflix.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
    private val popularMoviesMapper: PopularMoviesMapper,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val movieReviewsMapper: MovieReviewsMapper,
    private val similarMovieMapper: SimilarMovieMapper
) : MoviesRepository {
    override suspend fun getPopularMovies(page: Int): List<PopularMovie> {
        return popularMoviesMapper(remoteDataSource.getPopularMovies(page), localDataSource.getAllFavoriteMovies())
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails? {
        return movieDetailsMapper(remoteDataSource.getMovieDetails(movieId), localDataSource.getFavoriteMovieById(movieId))
    }

    override suspend fun getMovieReviews(movieId: Int): List<MovieReview> {
        return movieReviewsMapper(remoteDataSource.getMovieReviews(movieId))
    }

    override suspend fun getSimilarMovies(movieId: Int): List<SimilarMovie> {
        return similarMovieMapper(remoteDataSource.getSimilarMovies(movieId))
    }

    override suspend fun setFavoriteMovie(movieId: Int) {
        localDataSource.saveFavoriteMovie(LocalFavoriteMovie(movieId = movieId))
    }

    override suspend fun deleteFavoriteMovie(movieId: Int) {
        localDataSource.deleteFavoriteMovie(movieId)
    }
}