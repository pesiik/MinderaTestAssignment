package mc.pesiik.pt_android_iliamashin.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDto(
    val name: String,
    val owner: OwnerDto,
    val description: String?,
) {

    @Serializable
    data class OwnerDto(
        @SerialName("avatar_url") val avatarUrl: String
    )
}