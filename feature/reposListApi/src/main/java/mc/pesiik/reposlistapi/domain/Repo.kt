package mc.pesiik.reposlistapi.domain

data class Repo(
    val id: Int,
    val name: String,
    val starsCount: Int,
    val forksCount: Int,
    val lastUpdated: String,
    val description: String?,
    val language: String?,
    val user: User
)