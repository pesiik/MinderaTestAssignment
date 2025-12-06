package mc.pesiik.pt_android_iliamashin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mc.pesiik.pt_android_iliamashin.ui.components.ReposList
import mc.pesiik.pt_android_iliamashin.ui.components.SearchAppBar
import mc.pesiik.pt_android_iliamashin.ui.theme.Purple40
import mc.pesiik.pt_android_iliamashin.view.ReposListScreenEvent
import mc.pesiik.pt_android_iliamashin.view.ReposListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReposScreenList(
    vm: ReposListViewModel,
    onClose: () -> Unit,
) {
    val state by vm.state.collectAsState()

    when {
        state.shouldCloseApp -> onClose()

        state.isError -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // todo make a proper error screen with retry button
            Text(text = "Error")
        }


        else -> {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Purple40
                    )
                    .safeDrawingPadding(),
                topBar = {
                    SearchAppBar(
                        state = state,
                        onQueryChange = { query ->
                            vm.onEvent(ReposListScreenEvent.SearchRepos(query))
                        },
                        onSearchModeToggle = { isInSearchMode ->
                            vm.onEvent(ReposListScreenEvent.ToggleSearchMode(isInSearchMode))
                        },
                        onBackClicked = {
                            vm.onEvent(ReposListScreenEvent.BackButtonClicked)
                        },
                    )
                }
            ) { paddingValues ->
                ReposList(
                    state = state,
                    onItemClick = { repoId ->
                        vm.onEvent(ReposListScreenEvent.RepoClicked(repoId))
                    },
                    onLastItemVisible = { lastVisiblePosition ->
                        vm.onEvent(ReposListScreenEvent.ScrollReposList(lastVisiblePosition))
                    },
                    paddingValues = paddingValues,
                )
            }
        }
    }
}