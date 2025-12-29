package mc.pesiik.repodetailsimpl.data

import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import javax.inject.Inject

internal class RepoDetailsMapper @Inject constructor() {
    fun mapDtoToDomain(
        repoDto: RepoDetailsDto,
        ownerLogin: String,
        ownerAvatarUrl: String,
    ): RepoDetails {
        return RepoDetails(
            name = repoDto.name,
            description = repoDto.description,
            starsCount = repoDto.stargazersCount,
            forksCount = repoDto.forksCount,
            lastUpdated = repoDto.updatedAt,
            subscribersCount = repoDto.subscribersCount,
            ownerLogin = ownerLogin,
            ownerAvatarUrl = ownerAvatarUrl,
        )
    }
}