package mc.pesiik.pt_android_iliamashin.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mc.pesiik.pt_android_iliamashin.core.navigation.ReposListDestination
import mc.pesiik.pt_android_iliamashin.ui.list.components.ReposList
import mc.pesiik.pt_android_iliamashin.ui.list.components.SearchAppBar
import mc.pesiik.pt_android_iliamashin.ui.list.components.SortDialog
import mc.pesiik.pt_android_iliamashin.view.ReposListScreenEvent
import mc.pesiik.pt_android_iliamashin.view.ReposListViewModel

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

        state.isError -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // todo make a proper error screen with retry button
            Text(text = "Error")
        }

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