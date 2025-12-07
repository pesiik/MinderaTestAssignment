package mc.pesiik.reposlistimpl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mc.pesiik.navigation.ReposListDestination
import mc.pesiik.reposlistimpl.R
import mc.pesiik.reposlistimpl.ui.components.ReposList
import mc.pesiik.reposlistimpl.ui.components.SearchAppBar
import mc.pesiik.reposlistimpl.ui.components.SortDialog
import mc.pesiik.reposlistimpl.view.ReposListScreenEvent
import mc.pesiik.reposlistimpl.view.ReposListViewModel

fun NavGraphBuilder.reposListScreen(
    onRepoClick: (Int) -> Unit,
    onClose: () -> Unit,
) {
    composable(route = ReposListDestination.route) {
        ReposScreenList(
            onRepoClick = onRepoClick,
            onClose = onClose,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReposScreenList(
    viewModel: ReposListViewModel = hiltViewModel(),
    onRepoClick: (Int) -> Unit,
    onClose: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    when {
        state.shouldCloseApp -> onClose()

        state.isError -> SimpleErrorScreen(
            message = state.errorMessage ?: stringResource(
                R.string.repo_list_error_message
            ),
            onRetry = {
                viewModel.onEvent(ReposListScreenEvent.RetryLoadRepos)
            }
        )

        state.isSortMenuOpened -> {
            SortDialog(
                onSortOptionSelected = { sortOption ->
                    viewModel.onEvent(ReposListScreenEvent.SortedByOptionSelected(sortOption))
                },
                currentSortOption = state.selectedSortOption,
            )
        }


        else -> {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                    )
                    .safeDrawingPadding(),
                topBar = {
                    SearchAppBar(
                        state = state,
                        onQueryChange = { query ->
                            viewModel.onEvent(ReposListScreenEvent.SearchRepos(query))
                        },
                        onSearchModeToggle = { isInSearchMode ->
                            viewModel.onEvent(ReposListScreenEvent.ToggleSearchMode(isInSearchMode))
                        },
                        onSortClicked = {
                            viewModel.onEvent(ReposListScreenEvent.SortClicked)
                        },
                        onBackClicked = {
                            viewModel.onEvent(ReposListScreenEvent.BackButtonClicked)
                        },
                    )
                }
            ) { paddingValues ->
                ReposList(
                    state = state,
                    onItemClick = onRepoClick,
                    onLastItemVisible = { lastVisiblePosition ->
                        viewModel.onEvent(ReposListScreenEvent.ScrollReposList(lastVisiblePosition))
                    },
                    paddingValues = paddingValues,
                )
            }
        }
    }
}