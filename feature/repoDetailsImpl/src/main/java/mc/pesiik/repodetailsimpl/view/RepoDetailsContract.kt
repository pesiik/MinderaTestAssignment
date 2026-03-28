package mc.pesiik.repodetailsimpl.view

import androidx.compose.runtime.Immutable

@Immutable
internal data class RepoDetailUiState(
    val name: String = "",
    val description: String? = null,
    val forksCount: Int = 0,
    val starsCount: Int = 0,
    val subscribersCount: Int = 0,
    val lastUpdated: String = "",
    val ownerLogin: String? = null,
    val ownerAvatarUrl: String? = null,
    val isLoading: Boolean = true,
)