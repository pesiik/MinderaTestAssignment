package mc.pesiik.repodetailsimpl.interactor

import mc.pesiik.data.cache.TemporaryCache
import mc.pesiik.repodetailsapi.domain.RepoDetailsRepository
import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import mc.pesiik.reposlistapi.domain.Repo
import javax.inject.Inject

class RepoDetailsInteractor @Inject constructor(
    private val temporaryCache: TemporaryCache,
    private val repoDetailsRepository: RepoDetailsRepository
) {

    suspend fun getRepoDetails(repoId: Int): RepoData {
        val repo = requireNotNull(temporaryCache.getById<Repo>(repoId)) {
            "Repo cannot be null at this point. Repo id: $repoId"
        }
        val details = repoDetailsRepository.getRepoDetails(
            owner = repo.user.login,
            repo = repo.name
        )
        return repo to details
    }
}
typealias RepoData = Pair<Repo, RepoDetails>