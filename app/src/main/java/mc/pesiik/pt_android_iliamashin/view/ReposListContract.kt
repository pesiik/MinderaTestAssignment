package mc.pesiik.pt_android_iliamashin.view


sealed class ReposListScreenEvent {
    data class SearchRepos(val organization: String) : ReposListScreenEvent()
    data object SortClicked : ReposListScreenEvent()
}

sealed class ReposListState {
    object Loading : ReposListState()
    data class Success(val repos: List<RepoUiModel>) : ReposListState() {
        val isEmpty = repos.isEmpty()
    }

    data class Error(val message: String) : ReposListState()
}

data class RepoUiModel(
    val name: String,
    val ownerAvatarUrl: String,
    val description: String,
)