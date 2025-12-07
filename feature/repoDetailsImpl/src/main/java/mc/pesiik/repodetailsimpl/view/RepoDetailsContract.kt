package mc.pesiik.repodetailsimpl.view

data class RepoDetailUiState(
    val name: String = "",
    val description: String? = null,
    val forksCount: Int = 0,
    val starsCount: Int = 0,
    val subscribersCount: Int = 0,
    val lastUpdated: String = "",
    val isLoading: Boolean = true,
)