package com.example.repofinder.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Define GitHub User model
data class GitHubUser(
    val avatar_url: String,
    val login: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int,
    val html_url: String
)

// Define GitHub Repository model
data class GitHubRepo(
    val name: String,
    val html_url: String,
    val description: String?,
    val stargazers_count: Int
)

interface GitHubApiService {
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): Response<GitHubUser>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<GitHubRepo>>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<GitHubRepo>

}

