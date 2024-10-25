package com.example.repofinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.repofinder.model.RepoViewModel
import com.example.repofinder.ui.screens.RepoDetailScreen
import com.example.repofinder.ui.screens.RepoFinderScreen
import com.example.repofinder.ui.screens.RepoListScreen

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MaterialTheme {
                Surface {
                    val viewModel: RepoViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "repo-finder", builder = {
                        composable("repo-finder") {
                            RepoFinderScreen(viewModel, navController)
                        }

                        composable("repo-list/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username")
                            RepoListScreen(username, viewModel, navController)
                        }

                        composable("repo-detail/{repoName}") { backStackEntry ->
                            val repoName = backStackEntry.arguments?.getString("repoName")
                            if (repoName != null) {
                                RepoDetailScreen(repoName, viewModel, navController)
                            }
                        }

                    })
                }
            }

        }
    }
}
