package mc.pesiik.repodetailsimpl.data

import mc.pesiik.data.cache.TemporaryCache
import mc.pesiik.repodetailsapi.domain.RepoDetailsRepository
import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import javax.inject.Inject

internal class RepoDetailsRepositoryImpl @Inject constructor(
    private val gitRepoDetailService: GitRepoDetailService,
    private val repoDetailsMapper: RepoDetailsMapper,
) : RepoDetailsRepository {

    override suspend fun getRepoDetails(owner: String, repo: String): RepoDetails {
        val repoDto = gitRepoDetailService.getRepo(owner, repo)
        return repoDetailsMapper.mapDtoToDomain(repoDto)
    }
}