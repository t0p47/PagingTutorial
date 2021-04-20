package com.t0p47.pagingtutorial.model

sealed class RepoSearchResult {
    data class Success(val data: List<Repo>): RepoSearchResult()
    data class Error(val error: Exception): RepoSearchResult()
}