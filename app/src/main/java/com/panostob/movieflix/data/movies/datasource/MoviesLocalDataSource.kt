package com.panostob.movieflix.data.movies.datasource

import com.panostob.movieflix.data.movies.model.LocalPopularMovie

interface MoviesLocalDataSource {

    fun getPopularMovies(): List<LocalPopularMovie>
}