package com.example.repofinder.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// Define GitHub User model
data class GitHubUser(
    val login: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int,
    val html_url: String
)

interface GitHubApiService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<GitHubUser>
}
