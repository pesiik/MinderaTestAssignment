package mc.pesiik.pt_android_iliamashin.data

import mc.pesiik.pt_android_iliamashin.core.cache.ReposTemporaryCache
import mc.pesiik.pt_android_iliamashin.data.mapper.RepoDetailsMapper
import mc.pesiik.pt_android_iliamashin.domain.RepoDetails
import mc.pesiik.pt_android_iliamashin.domain.RepoDetailsRepository
import javax.inject.Inject

class RepoDetailsRepositoryImpl @Inject constructor(
    private val reposTemporaryCache: ReposTemporaryCache,
    private val gitRepoDetailService: GitRepoDetailService,
    private val repoDetailsMapper: RepoDetailsMapper,
) : RepoDetailsRepository {

    override suspend fun getRepoDetails(repoId: Int): RepoDetails {
        val repo = requireNotNull(reposTemporaryCache.getRepoById(repoId)) {
            "Repo cannot be null at this point. Repo id: $repoId"
        }
        val repoDto = gitRepoDetailService.getRepo(repo.ownerLogin, repo.name)
        return repoDetailsMapper.mapDtoToDomain(repoDto)
    }
}