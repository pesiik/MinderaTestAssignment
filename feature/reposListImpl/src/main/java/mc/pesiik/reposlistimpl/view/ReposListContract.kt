package mc.pesiik.reposlistimpl.view

import androidx.compose.runtime.Immutable


internal sealed class ReposListScreenEvent {
    @Immutable
    data class SearchRepos(val query: String) : ReposListScreenEvent()

    @Immutable
    data class ToggleSearchMode(val isInSearchMode: Boolean) : ReposListScreenEvent()

    @Immutable
    data class ScrollReposList(val lastVisiblePosition: Int) : ReposListScreenEvent()

    @Immutable
    data object SortClicked : ReposListScreenEvent()

    @Immutable
    data class SortedByOptionSelected(val sortOption: ReposSortOption) : ReposListScreenEvent()

    @Immutable
    data object RetryLoadRepos : ReposListScreenEvent()

    @Immutable
    data object BackButtonClicked : ReposListScreenEvent()
}

@Immutable
internal data class ReposListState(
    val repos: List<RepoUiModel> = emptyList(),
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

@Immutable
internal data class RepoUiModel(
    val id: Int,
    val isShimmer: Boolean = true,
    val name: String = "",
    val ownerLogin: String = "",
    val ownerAvatarUrl: String = "",
    val description: String = "",
    val starCount: Int = 0,
    val language: String? = null,
)

internal enum class ReposSortOption(val value: String?) {
    STARS("stars"),
    FORKS("forks"),
    UPDATED("updated"),
    NOT_CHOSEN(null),
}