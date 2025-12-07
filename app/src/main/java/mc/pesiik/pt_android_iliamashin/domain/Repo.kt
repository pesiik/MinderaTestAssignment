package mc.pesiik.pt_android_iliamashin.domain

data class Repo(
    val id: Int,
    val name: String,
    val starsCount: Int,
    val forksCount: Int,
    val lastUpdated: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val description: String?,
    val language: String?,
)