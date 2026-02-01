package mc.pesiik.reposlistimpl.data

import mc.pesiik.reposlistapi.domain.Repo
import mc.pesiik.reposlistapi.domain.User
import javax.inject.Inject

internal class ReposMapper @Inject constructor() {
    fun mapDtoToDomain(
        repoDto: RepoDto
    ): Repo {
        return Repo(
            id = repoDto.id,
            name = repoDto.name,
            description = repoDto.description,
            starsCount = repoDto.stargazersCount,
            forksCount = repoDto.forksCount,
            lastUpdated = repoDto.updatedAt,
            language = repoDto.language,
            user = mapUser(ownerDto = repoDto.owner)
        )
    }

    private fun mapUser(ownerDto: RepoDto.OwnerDto): User {
        return User(
            login = ownerDto.login,
            avatarUrl = ownerDto.avatarUrl,
        )
    }
}