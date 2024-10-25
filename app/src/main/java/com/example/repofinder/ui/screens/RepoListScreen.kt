package com.example.repofinder.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.repofinder.api.GitHubRepo
import com.example.repofinder.model.RepoViewModel


@Composable
fun RepoListScreen(username: String?, viewModel: RepoViewModel, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "All Repositories of $username", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.repos) { repo ->
                RepoItem(repo = repo) {
                    navController.navigate("repo-detail/${repo.name}")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                if (viewModel.hasMoreRepos) {
                    Button(
                        onClick = { viewModel.loadMoreRepos(username ?: "") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(6.dp))
                    ) {
                        Text("Load More")
                    }
                }
            }
        }
    }
}


@Composable
fun RepoItem(repo: GitHubRepo, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {onClick()}
    ) {
        Text(text = repo.name, style = MaterialTheme.typography.bodyLarge)
        Text(text = repo.description ?: "No description", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Stars: ${repo.stargazers_count}", style = MaterialTheme.typography.bodyLarge)
    }
}

