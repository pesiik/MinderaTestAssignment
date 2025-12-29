package mc.pesiik.repodetailsimpl.view

import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import javax.inject.Inject

internal class RepoDetailStateMapper @Inject constructor() {

    fun mapToUiState(repo: RepoDetails): RepoDetailUiState {
        return RepoDetailUiState(
            name = repo.name,
            description = repo.description,
            forksCount = repo.forksCount,
            starsCount = repo.starsCount,
            subscribersCount = repo.subscribersCount,
            lastUpdated = repo.lastUpdated,
            ownerLogin = repo.ownerLogin,
            ownerAvatarUrl = repo.ownerAvatarUrl,
            isLoading = false,
        )
    }
}