package mc.pesiik.reposlistimpl.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReposDto(
    @SerialName("incomplete_results") val incompleteResults: Boolean,
    val items: List<RepoDto>
)