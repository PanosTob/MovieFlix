package com.panostob.movieflix.ui.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.panostob.movieflix.domain.movies.entity.PopularMovie

class PopularMoviesPagingSource(
    private val getPopularMoviesPagingData: suspend (Int) -> List<PopularMovie>?,
) : PagingSource<Int, PopularMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMovie> {
        return try {
            //for first case it will be null, then we can pass some default value, in our case it's 1
            val page = params.key ?: 0

            val data = getPopularMoviesPagingData(page) ?: return LoadResult.Error(Exception("Data is null"))

            LoadResult.Page(
                data = data,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PopularMovie>): Int? {
        return state.anchorPosition
    }
}