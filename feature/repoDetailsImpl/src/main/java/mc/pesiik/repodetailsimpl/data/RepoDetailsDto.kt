package mc.pesiik.repodetailsimpl.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
internal data class RepoDetailsDto(
    val name: String,
    val description: String?,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("forks_count") val forksCount: Int,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("subscribers_count") val subscribersCount: Int,
)