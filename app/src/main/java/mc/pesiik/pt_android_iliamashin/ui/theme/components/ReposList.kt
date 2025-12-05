package mc.pesiik.pt_android_iliamashin.ui.theme.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mc.pesiik.pt_android_iliamashin.R
import mc.pesiik.pt_android_iliamashin.view.ReposListState

@Composable
fun ReposList(
    state: ReposListState.Success
) {
    Row(
        modifier = Modifier.wrapContentWidth()
    ) {
        Text(
            text = stringResource(R.string.repos_list_title),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp),
        )
        if (state.isEmpty) {
            Text(
                text = stringResource(R.string.repos_list_empty),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp),
            )
        } else {
            // todo
        }
    }
}