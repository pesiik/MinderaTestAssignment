package mc.pesiik.pt_android_iliamashin.domain

data class Repo(
    val id: Int,
    val name: String,
    val starCount: Int,
    val forkCount: Int,
    val watcherCount: Int,
    val lastUpdated: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val description: String?,
    val language: String?,
)