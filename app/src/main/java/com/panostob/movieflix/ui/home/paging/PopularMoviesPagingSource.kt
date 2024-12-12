package com.panostob.movieflix.ui.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.panostob.movieflix.domain.movies.entity.PopularMovie
import com.panostob.movieflix.ui.home.model.PopularMovieUiItem

class PopularMoviesPagingSource(
    private val getPopularMoviesPagingData: suspend (Int) -> List<PopularMovie>?,
    private val mapPopularMoviesToUiItems: (List<PopularMovie>) -> List<PopularMovieUiItem>
) : PagingSource<Int, PopularMovieUiItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMovieUiItem> {
        return try {
            //for first case it will be null, then we can pass some default value, in our case it's 1
            val page = params.key ?: 1

            val data = getPopularMoviesPagingData(page) ?: return LoadResult.Error(Exception("Data is null"))

            val moviesUi = mapPopularMoviesToUiItems(data)

            LoadResult.Page(
                data = moviesUi,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PopularMovieUiItem>): Int? {
        return state.anchorPosition
    }
}