package mc.pesiik.pt_android_iliamashin.data

import mc.pesiik.pt_android_iliamashin.core.navigation.cache.ReposTemporaryCache
import mc.pesiik.pt_android_iliamashin.domain.Repo
import mc.pesiik.pt_android_iliamashin.domain.RepoDetailsRepository
import javax.inject.Inject

class RepoDetailsRepositoryImpl @Inject constructor(
    private val reposTemporaryCache: ReposTemporaryCache
) : RepoDetailsRepository {

    override fun getRepoDetails(repoId: Int): Repo {
        return requireNotNull(reposTemporaryCache.getRepoById(repoId)) {
            "Repo cannot be null at this point. Repo id: $repoId"
        }
    }
}