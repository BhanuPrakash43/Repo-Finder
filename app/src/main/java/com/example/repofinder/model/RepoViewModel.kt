package com.example.repofinder.model

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repofinder.api.GitHubApiService
import com.example.repofinder.api.GitHubRepo
import com.example.repofinder.api.GitHubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepoViewModel : ViewModel() {

    private val apiService: GitHubApiService = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubApiService::class.java)

    var user by mutableStateOf<GitHubUser?>(null)
        private set

    var repos by mutableStateOf<List<GitHubRepo>>(emptyList())
        private set

    var repoDetails by mutableStateOf<GitHubRepo?>(null)
        private set

    var currentPage by mutableStateOf(1)
    var pageSize by mutableStateOf(10)
    var hasMoreRepos by mutableStateOf(false)

    fun searchUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiService.getUser(username)
            if (response.isSuccessful) {
                user = response.body()
            }
        }
    }

    fun fetchUserRepos(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiService.getUserRepos(username, currentPage, pageSize)
            if (response.isSuccessful) {
                val newRepos = response.body() ?: emptyList()
                repos = repos + newRepos

                hasMoreRepos = newRepos.size == pageSize
            }
        }
    }

    fun fetchRepoDetails(owner: String, repoName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiService.getRepoDetails(owner, repoName)
            if (response.isSuccessful) {
                repoDetails = response.body()
            } else {
                repoDetails = null
            }
        }
    }

    fun loadMoreRepos(username: String) {
        currentPage++
        fetchUserRepos(username)
    }
}
