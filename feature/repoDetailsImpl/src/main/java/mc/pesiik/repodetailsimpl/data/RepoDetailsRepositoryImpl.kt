package mc.pesiik.repodetailsimpl.data

import mc.pesiik.data.cache.TemporaryCache
import mc.pesiik.repodetailsapi.domain.RepoDetailsRepository
import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import mc.pesiik.reposlistapi.domain.Repo
import javax.inject.Inject

internal class RepoDetailsRepositoryImpl @Inject constructor(
    private val temporaryCache: TemporaryCache,
    private val gitRepoDetailService: GitRepoDetailService,
    private val repoDetailsMapper: RepoDetailsMapper,
) : RepoDetailsRepository {

    override suspend fun getRepoDetails(repoId: Int): RepoDetails {
        val repo = requireNotNull(temporaryCache.getById<Repo>(repoId)) {
            "Repo cannot be null at this point. Repo id: $repoId"
        }
        val repoDto = gitRepoDetailService.getRepo(repo.ownerLogin, repo.name)
        return repoDetailsMapper.mapDtoToDomain(
            repoDto = repoDto,
            ownerLogin = repo.ownerLogin,
            ownerAvatarUrl = repo.ownerAvatarUrl
        )
    }
}