package mc.pesiik.pt_android_iliamashin.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mc.pesiik.pt_android_iliamashin.ui.theme.components.ReposList
import mc.pesiik.pt_android_iliamashin.view.ReposListState
import mc.pesiik.pt_android_iliamashin.view.ReposListViewModel

@Composable
fun ReposScreenList(vm: ReposListViewModel) {
    val state by vm.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        when (state) {
            ReposListState.Loading -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // todo make a proper loading screen
                Text(text = "Loading...")
            }

            is ReposListState.Error -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // todo make a proper error screen with retry button
                Text(text = "Error")
            }

            is ReposListState.Success -> ReposList(state as ReposListState.Success)
        }
    }
}