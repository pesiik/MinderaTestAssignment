package mc.pesiik.pt_android_iliamashin.data.mapper

import mc.pesiik.pt_android_iliamashin.data.model.RepoDetailsDto
import mc.pesiik.pt_android_iliamashin.domain.RepoDetails
import javax.inject.Inject

class RepoDetailsMapper @Inject constructor() {
    fun mapDtoToDomain(
        repoDto: RepoDetailsDto
    ): RepoDetails {
        return RepoDetails(
            name = repoDto.name,
            description = repoDto.description,
            starsCount = repoDto.stargazersCount,
            forksCount = repoDto.forksCount,
            lastUpdated = repoDto.updatedAt,
            subscribersCount = repoDto.subscribersCount,
        )
    }
}