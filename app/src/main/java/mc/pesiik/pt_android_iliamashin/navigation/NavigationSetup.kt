package mc.pesiik.pt_android_iliamashin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import mc.pesiik.pt_android_iliamashin.core.navigation.RepoDetailsDestination
import mc.pesiik.pt_android_iliamashin.core.navigation.ReposListDestination
import mc.pesiik.pt_android_iliamashin.ui.details.navigateToRepoDetails
import mc.pesiik.pt_android_iliamashin.ui.details.repoDetailsScreen
import mc.pesiik.pt_android_iliamashin.ui.list.reposListScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    onClose: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = ReposListDestination.route
    ) {
        reposListScreen(
            onRepoClick = { repoId ->
                navController.navigateToRepoDetails(repoId)
            },
            onClose = onClose
        )

        repoDetailsScreen(
            onBackClick = { navController.popBackStack() }
        )
    }
}