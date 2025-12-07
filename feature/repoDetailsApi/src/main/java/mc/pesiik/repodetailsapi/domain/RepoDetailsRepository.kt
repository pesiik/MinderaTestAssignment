package mc.pesiik.repodetailsapi.domain

import mc.pesiik.repodetailsapi.domain.model.RepoDetails

interface RepoDetailsRepository {
    suspend fun getRepoDetails(repoId: Int): RepoDetails
}