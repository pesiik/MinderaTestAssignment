package mc.pesiik.pt_android_iliamashin.ui.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import mc.pesiik.pt_android_iliamashin.R
import mc.pesiik.pt_android_iliamashin.ui.theme.Typography
import mc.pesiik.pt_android_iliamashin.view.ReposListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    state: ReposListState,
    onQueryChange: (String) -> Unit,
    onSearchModeToggle: (Boolean) -> Unit,
    onSortClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    if (state.isInSearchMode) {
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackButton(onBackClicked = onBackClicked)
            AppBarWithSearchAndTitle(
                state = state,
                onQueryChange = onQueryChange,
                focusRequester = focusRequester
            )
            SortButton(onSortClicked)
        }
    } else {
        TopAppBar(
            actions = {
                IconButton(onClick = { onSearchModeToggle(true) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = stringResource(R.string.repos_list_search_content_description)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                scrolledContainerColor = MaterialTheme.colorScheme.secondary,
            ),
            title = {
                AppBarTitle()
            },
        )
    }
}

@OptIn
@Composable
private fun BackButton(
    onBackClicked: () -> Unit,
) {
    IconButton(onClick = onBackClicked) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = stringResource(R.string.back_button)
        )
    }
}

// kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBarWithSearchAndTitle(
    state: ReposListState,
    focusRequester: FocusRequester,
    onQueryChange: (String) -> Unit,
) {
    TextField(
        modifier = Modifier
            .wrapContentWidth()
            .height(
                TopAppBarDefaults.TopAppBarExpandedHeight
            )
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
        ),
        value = state.searchQuery,
        onValueChange = onQueryChange,
        singleLine = true,
        textStyle = Typography.titleLarge,
        placeholder = {
            Text(
                text = stringResource(R.string.repos_list_search_placeholder),
                style = Typography.titleLarge,
            )
        }
    )
}

@Composable
private fun SortButton(
    onSortClicked: () -> Unit,
) {
    IconButton(onClick = onSortClicked) {
        Icon(
            imageVector = Icons.Default.Settings,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = stringResource(R.string.repo_list_sort_button)
        )
    }
}

@Composable
private fun AppBarTitle() {
    Text(
        text = stringResource(R.string.repos_list_title),
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = 30.sp,
    )
}