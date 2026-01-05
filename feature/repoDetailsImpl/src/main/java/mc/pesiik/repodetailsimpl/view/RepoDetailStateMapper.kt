package mc.pesiik.repodetailsimpl.view

import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import javax.inject.Inject

internal class RepoDetailStateMapper @Inject constructor() {

    fun mapToUiState(
        details: RepoDetails,
        ownerLogin: String,
        ownerAvatarUrl: String,
    ): RepoDetailUiState {
        return RepoDetailUiState(
            name = details.name,
            description = details.description,
            forksCount = details.forksCount,
            starsCount = details.starsCount,
            subscribersCount = details.subscribersCount,
            lastUpdated = details.lastUpdated,
            ownerLogin = ownerLogin,
            ownerAvatarUrl = ownerAvatarUrl,
            isLoading = false,
        )
    }
}