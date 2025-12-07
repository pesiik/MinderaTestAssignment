package mc.pesiik.navigation

interface NavigationDestination {
    val route: String
}

object ReposListDestination : NavigationDestination {
    override val route = "repos_list"
}

object RepoDetailsDestination : NavigationDestination {
    override val route = "repo_details"
    const val REPO_ID_ARG = "repoId"
    val routeWithArgs = "$route/{$REPO_ID_ARG}"

    fun createRoute(repoId: Int) = "$route/$repoId"
}