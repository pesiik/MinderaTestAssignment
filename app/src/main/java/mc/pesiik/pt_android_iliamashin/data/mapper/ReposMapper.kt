package mc.pesiik.pt_android_iliamashin.data.mapper

import mc.pesiik.pt_android_iliamashin.data.model.RepoDto
import mc.pesiik.pt_android_iliamashin.domain.Repo
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
            starCount = repoDto.stargazersCount,
            forkCount = repoDto.forksCount,
            watcherCount = repoDto.watchersCount,
            lastUpdated = repoDto.updatedAt,
            language = repoDto.language,
        )
    }
}