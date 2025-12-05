package mc.pesiik.pt_android_iliamashin.data

import mc.pesiik.pt_android_iliamashin.data.mapper.ReposMapper
import mc.pesiik.pt_android_iliamashin.domain.Repo
import mc.pesiik.pt_android_iliamashin.domain.ReposRepository
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
    private val gitReposService: GitReposService,
    private val reposMapper: ReposMapper,
) : ReposRepository {

    override suspend fun searchRepos(organization: String): List<Repo> {
        val repoDtos = gitReposService.searchRepos(organization)
        return repoDtos.map(reposMapper::mapDtoToDomain)
    }
}