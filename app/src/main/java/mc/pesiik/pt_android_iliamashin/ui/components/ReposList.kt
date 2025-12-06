package mc.pesiik.pt_android_iliamashin.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import mc.pesiik.pt_android_iliamashin.R
import mc.pesiik.pt_android_iliamashin.view.ReposListState

@Composable
fun ReposList(
    state: ReposListState,
    onItemClick: (Int) -> Unit,
    onLastItemVisible: (Int) -> Unit,
    paddingValues: PaddingValues,
) {
    if (state.isEmpty) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.repos_list_empty),
                textAlign = TextAlign.Center
            )
        }
    } else {
        val gridState = rememberLazyGridState()

        LaunchedEffect(gridState) {
            snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .distinctUntilChanged()
                .collect { lastVisibleIndex ->
                    lastVisibleIndex?.let { onLastItemVisible(it) }
                }
        }

        LazyVerticalGrid(
            state = gridState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),

        ) {
            items(state.repos.size) { index ->
                val repo = state.repos[index]
                RepoCard(
                    repo = repo,
                    onClick = onItemClick
                )
            }
        }
    }
}