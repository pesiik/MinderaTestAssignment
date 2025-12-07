package mc.pesiik.pt_android_iliamashin.data.mapper

import mc.pesiik.pt_android_iliamashin.data.model.RepoDto
import mc.pesiik.reposlistapi.domain.Repo
import javax.inject.Inject

class ReposMapper @Inject constructor() {
    fun mapDtoToDomain(
        repoDto: RepoDto
    ): Repo {
        return Repo(
            id = repoDto.id,
            name = repoDto.name,
            ownerLogin = repoDto.owner.login,
            ownerAvatarUrl = repoDto.owner.avatarUrl,
            description = repoDto.description,
            starsCount = repoDto.stargazersCount,
            forksCount = repoDto.forksCount,
            lastUpdated = repoDto.updatedAt,
            language = repoDto.language,
        )
    }
}