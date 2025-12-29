package mc.pesiik.repodetailsapi.domain.model

data class RepoDetails(
    val subscribersCount: Int,
    val name: String,
    val description: String?,
    val forksCount: Int,
    val starsCount: Int,
    val lastUpdated: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
)