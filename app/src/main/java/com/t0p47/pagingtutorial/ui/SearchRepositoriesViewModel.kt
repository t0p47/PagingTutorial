package com.t0p47.pagingtutorial.ui

import androidx.lifecycle.*
import com.t0p47.pagingtutorial.data.GithubRepository
import com.t0p47.pagingtutorial.model.RepoSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchRepositoriesViewModel @Inject constructor(private val repository: GithubRepository): ViewModel() {

    companion object{
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    val repoResult: LiveData<RepoSearchResult> = queryLiveData.switchMap { queryString ->
        liveData {
            val repos = repository.getSearchResultStream(queryString).asLiveData(Dispatchers.Main)
            emitSource(repos)
        }
    }

    fun searchRepo(queryString: String){
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int){
        if(visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount){
            val immutableQuery = queryLiveData.value
            if(immutableQuery != null){
                viewModelScope.launch {
                    repository.requestMore(immutableQuery)
                }
            }
        }
    }
}