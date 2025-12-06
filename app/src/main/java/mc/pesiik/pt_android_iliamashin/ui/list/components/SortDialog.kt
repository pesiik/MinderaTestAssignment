package mc.pesiik.pt_android_iliamashin.ui.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mc.pesiik.pt_android_iliamashin.R
import mc.pesiik.pt_android_iliamashin.view.ReposSortOption

@Composable
fun SortDialog(
    onSortOptionSelected: (ReposSortOption) -> Unit,
    currentSortOption: ReposSortOption,
) {
    var selectedOption by remember { mutableStateOf(currentSortOption) }

    AlertDialog(
        onDismissRequest = {
            onSortOptionSelected(selectedOption)
        },
        title = {
            Text(text = stringResource(R.string.repo_list_sort_by_title))
        },
        text = {
            Column {
                SortOptionItem(
                    option = ReposSortOption.STARS,
                    selectedOption = selectedOption,
                    onOptionClick = {
                        selectedOption = if (selectedOption == ReposSortOption.STARS) {
                            ReposSortOption.NOT_CHOSEN
                        } else {
                            ReposSortOption.STARS
                        }
                    }
                )
                SortOptionItem(
                    option = ReposSortOption.FORKS,
                    selectedOption = selectedOption,
                    onOptionClick = {
                        selectedOption = if (selectedOption == ReposSortOption.FORKS) {
                            ReposSortOption.NOT_CHOSEN
                        } else {
                            ReposSortOption.FORKS
                        }
                    }
                )
                SortOptionItem(
                    option = ReposSortOption.UPDATED,
                    selectedOption = selectedOption,
                    onOptionClick = {
                        selectedOption = if (selectedOption == ReposSortOption.UPDATED) {
                            ReposSortOption.NOT_CHOSEN
                        } else {
                            ReposSortOption.UPDATED
                        }
                    }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSortOptionSelected(selectedOption)
                }
            ) {
                Text(text = stringResource(R.string.repo_list_sort_ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onSortOptionSelected(currentSortOption)
                }
            ) {
                Text(text = stringResource(R.string.repo_list_sort_cancel))
            }
        }
    )
}

@Composable
private fun SortOptionItem(
    option: ReposSortOption,
    selectedOption: ReposSortOption,
    onOptionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOptionClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedOption == option,
            onClick = onOptionClick
        )
        Text(
            text = option.name.lowercase().replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
