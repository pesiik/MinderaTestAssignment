package mc.pesiik.pt_android_iliamashin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import mc.pesiik.pt_android_iliamashin.ui.theme.Purple40
import mc.pesiik.pt_android_iliamashin.ui.theme.PurpleGrey40
import mc.pesiik.pt_android_iliamashin.ui.theme.Typography
import mc.pesiik.pt_android_iliamashin.view.ReposListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    state: ReposListState,
    onQueryChange: (String) -> Unit,
    onSearchModeToggle: (Boolean) -> Unit,
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
                    color = Purple40
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackButton(onBackClicked = onBackClicked)
            AppBarWithSearchAndTitle(
                state = state,
                onQueryChange = onQueryChange,
                focusRequester = focusRequester
            )
        }
    } else {
        TopAppBar(
            actions = {
                IconButton(onClick = { onSearchModeToggle(true) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Purple40,
                scrolledContainerColor = PurpleGrey40,
            ),
            title = {
                if (!state.isInSearchMode) {
                    AppBarTitle()
                }
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
            focusedIndicatorColor = PurpleGrey40,
            unfocusedIndicatorColor = PurpleGrey40,
            focusedContainerColor = Purple40,
            unfocusedContainerColor = Purple40,
        ),
        value = state.searchQuery,
        onValueChange = onQueryChange,
        singleLine = true,
        textStyle = Typography.titleLarge,
    )
}

@Composable
private fun TrallingIcon(
    searchQuery: String,
    onQueryChange: (String) -> Unit
) {
    if (searchQuery.isNotEmpty()) {
        IconButton(onClick = { onQueryChange("") }) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear"
            )
        }
    }
}

@Composable
private fun AppBarTitle() {
    Text(
        text = stringResource(R.string.repos_list_title),
        fontSize = 30.sp,
    )
}