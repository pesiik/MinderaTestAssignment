package mc.pesiik.repodetailsimpl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import mc.pesiik.navigation.RepoDetailsDestination
import mc.pesiik.repodetailsimpl.ui.components.RepoDetailsContent
import mc.pesiik.repodetailsimpl.view.RepoDetailsViewModel


fun NavController.navigateToRepoDetails(repoId: Int) {
    navigate(RepoDetailsDestination.createRoute(repoId))
}

fun NavGraphBuilder.repoDetailsScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = RepoDetailsDestination.routeWithArgs,
        arguments = listOf(
            navArgument(RepoDetailsDestination.REPO_ID_ARG) {
                type = NavType.IntType
            }
        )
    ) {
        RepoDetailsScreen(
            viewModel = hiltViewModel<RepoDetailsViewModel, RepoDetailsViewModel.Factory>(
                key = it.arguments?.getInt(RepoDetailsDestination.REPO_ID_ARG).toString(),
            ) { factory ->
                val repoId = it.arguments?.getInt(
                    RepoDetailsDestination.REPO_ID_ARG
                ) ?: error("Repo id is required")
                factory.create(repoId)
            },
            onBackClick = onBackClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailsScreen(
    viewModel: RepoDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primary,
            )
            .safeDrawingPadding(),
        topBar = {
            RepoDetailsTopAppBar(
                title = state.name,
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        RepoDetailsContent(
            state = state,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepoDetailsTopAppBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.secondary,
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(
                        id = com.yourapp.core.resources.R.string.back_button
                    ),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}