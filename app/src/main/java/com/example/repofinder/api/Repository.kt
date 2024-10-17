package com.example.repofinder.api

data class SearchResponse(
    val total_count: Int,
    val items: List<Repository>
)

data class Repository(
    val name: String,
    val description: String?,
    val owner: Owner,
    val html_url: String
)

data class Owner(
    val avatar_url: String,
    val login: String
)
