package mc.pesiik.pt_android_iliamashin.ui.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import mc.pesiik.pt_android_iliamashin.core.navigation.RepoDetailsDestination
import mc.pesiik.pt_android_iliamashin.domain.RepoDetailsRepository
import mc.pesiik.pt_android_iliamashin.view.RepoDetailsViewModel


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
                val repoId = it.arguments?.getInt(RepoDetailsDestination.REPO_ID_ARG) ?: error("Repo id is required")
                factory.create(repoId)
            },
        )
    }
}

@Composable
fun RepoDetailsScreen(
    viewModel: RepoDetailsViewModel = hiltViewModel(),
) {
    Text("Kek")
}