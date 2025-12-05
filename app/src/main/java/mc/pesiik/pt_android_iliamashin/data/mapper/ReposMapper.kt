package mc.pesiik.pt_android_iliamashin.data.mapper

import mc.pesiik.pt_android_iliamashin.data.model.RepoDto
import mc.pesiik.pt_android_iliamashin.domain.Repo
import javax.inject.Inject

class ReposMapper @Inject constructor() {
    fun mapDtoToDomain(
        repoDto: RepoDto
    ): Repo {
        return Repo(
            name = repoDto.name,
            ownerAvatarUrl = repoDto.owner.avatarUrl,
            description = repoDto.description
        )
    }
}