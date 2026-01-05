package mc.pesiik.repodetailsapi.domain

import mc.pesiik.repodetailsapi.domain.model.RepoDetails

interface RepoDetailsRepository {
    suspend fun getRepoDetails(owner: String, repo: String): RepoDetails
}