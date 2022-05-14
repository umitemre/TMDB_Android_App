package com.example.mobilliumcase.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mobilliumcase.model.Result
import com.example.mobilliumcase.network.ApiRequest

private const val STARTING_PAGE_INDEX = 1

class UpcomingPagingSource(var apiRequest: ApiRequest) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return STARTING_PAGE_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: STARTING_PAGE_INDEX
        val data = apiRequest.getUpcoming(page)

        return try {
            LoadResult.Page(
                data = data.results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (data.results.size == 0) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}