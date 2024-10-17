package com.example.repofinder.model

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repofinder.api.GitHubApiService
import com.example.repofinder.api.GitHubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ViewModel to handle API calls
class RepoViewModel : ViewModel() {

    private val apiService: GitHubApiService = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubApiService::class.java)

    var user by mutableStateOf<GitHubUser?>(null)
        private set

    fun searchUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiService.getUser(username)
            if (response.isSuccessful) {
                user = response.body()
            }
        }
    }
}
