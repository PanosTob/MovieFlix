package com.panostob.movieflix.domain.movies.entity

sealed class UpdateDatasourceResult {
    data object Success: UpdateDatasourceResult()
    data object Failed: UpdateDatasourceResult()
}