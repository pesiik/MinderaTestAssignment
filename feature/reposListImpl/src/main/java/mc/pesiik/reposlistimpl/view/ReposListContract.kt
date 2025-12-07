package mc.pesiik.reposlistimpl.view


sealed class ReposListScreenEvent {
    data class SearchRepos(val query: String) : ReposListScreenEvent()
    data class ToggleSearchMode(val isInSearchMode: Boolean) : ReposListScreenEvent()
    data class ScrollReposList(val lastVisiblePosition: Int) : ReposListScreenEvent()
    data object SortClicked : ReposListScreenEvent()
    data class SortedByOptionSelected(val sortOption: ReposSortOption) : ReposListScreenEvent()
    data object RetryLoadRepos : ReposListScreenEvent()
    data object BackButtonClicked : ReposListScreenEvent()
}

data class ReposListState(
    val repos: List<RepoUiModel> = emptyList(),
    val isIdle: Boolean = false,
    val isInSearchMode: Boolean = false,
    val searchQuery: String = "",
    val errorMessage: String? = null,
    val shouldCloseApp: Boolean = false,
    val isSortMenuOpened: Boolean = false,
    val selectedSortOption: ReposSortOption = ReposSortOption.NOT_CHOSEN,
) {
    val isEmpty = repos.isEmpty()
    val isError = errorMessage != null
}

data class RepoUiModel(
    val isShimmer: Boolean = true,
    val id: Int = 0,
    val name: String = "",
    val ownerLogin: String = "",
    val ownerAvatarUrl: String = "",
    val description: String = "",
    val starCount: Int = 0,
    val language: String? = null,
)

enum class ReposSortOption(val value: String?) {
    STARS("stars"),
    FORKS("forks"),
    UPDATED("updated"),
    NOT_CHOSEN(null),
}