package mc.pesiik.pt_android_iliamashin.domain

data class RepoDetails(
    val subscribersCount: Int,
    val name: String,
    val description: String?,
    val forksCount: Int,
    val starsCount: Int,
    val lastUpdated: String,
)