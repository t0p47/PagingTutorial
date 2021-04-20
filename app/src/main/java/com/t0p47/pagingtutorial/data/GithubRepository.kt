package com.t0p47.pagingtutorial.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.t0p47.pagingtutorial.api.GithubService
import com.t0p47.pagingtutorial.model.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val GITHUB_STARTING_PAGE_INDEX = 1

@ExperimentalCoroutinesApi
class GithubRepository @Inject constructor(private val service: GithubService) {


    fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {

        Log.d("LOG_TAG", "GithubRepository: getSearchResultStream: query: $query")

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(service, query) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }

}