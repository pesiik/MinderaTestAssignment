package mc.pesiik.reposlistimpl.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDto(
    val id: Int,
    val name: String,
    val owner: OwnerDto,
    val description: String?,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("forks_count") val forksCount: Int,
    @SerialName("updated_at") val updatedAt: String,
    val language: String?,
) {

    @Serializable
    data class OwnerDto(
        val login: String,
        @SerialName("avatar_url") val avatarUrl: String
    )
}