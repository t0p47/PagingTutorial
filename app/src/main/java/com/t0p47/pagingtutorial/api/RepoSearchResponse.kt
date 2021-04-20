package com.t0p47.pagingtutorial.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.t0p47.pagingtutorial.model.Repo

@JsonClass(generateAdapter = true)
data class RepoSearchResponse(
    @Json(name = "total_count") val total: Int = 0,
    @Json(name = "items") val items: List<Repo> = emptyList(),
    val nextPage: Int? = null
)