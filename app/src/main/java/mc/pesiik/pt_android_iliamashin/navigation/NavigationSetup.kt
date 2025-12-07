package mc.pesiik.pt_android_iliamashin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import mc.pesiik.navigation.ReposListDestination
import mc.pesiik.repodetailsimpl.ui.navigateToRepoDetails
import mc.pesiik.repodetailsimpl.ui.repoDetailsScreen
import mc.pesiik.reposlistimpl.ui.reposListScreen

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