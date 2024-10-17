package com.example.repofinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.repofinder.model.RepoViewModel

@ExperimentalMaterial3Api
@Composable
fun RepoFinderScreen(viewModel: RepoViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Repo Finder",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search Bar
        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChange = {
                searchQuery = it
                if (searchQuery.isNotEmpty()) {
                    viewModel.searchUser(searchQuery)
                }
            }
        )

        // Display GitHub User Details
        viewModel.user?.let { user ->
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.LightGray, RoundedCornerShape(12.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Username: ${user.login}", fontSize = 20.sp)
                Text(text = "Public Repos: ${user.public_repos}", fontSize = 16.sp)
                Text(text = "Followers: ${user.followers}", fontSize = 16.sp)
                Text(text = "Following: ${user.following}", fontSize = 16.sp)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun SearchBar(searchQuery: String, onSearchQueryChange: (String) -> Unit) {

    TextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChange(it) },
        placeholder = { Text("Search GitHub username...") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray),
        textStyle = TextStyle(
            fontSize = 20.sp
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.LightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
