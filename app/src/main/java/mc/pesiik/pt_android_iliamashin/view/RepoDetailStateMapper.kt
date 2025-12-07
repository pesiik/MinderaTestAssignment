package mc.pesiik.pt_android_iliamashin.view

import mc.pesiik.pt_android_iliamashin.domain.RepoDetails
import javax.inject.Inject

class RepoDetailStateMapper @Inject constructor() {

    fun mapToUiState(repo: RepoDetails): RepoDetailUiState {
        return RepoDetailUiState(
            name = repo.name,
            description = repo.description,
            forksCount = repo.forksCount,
            starsCount = repo.starsCount,
            subscribersCount = repo.subscribersCount,
            lastUpdated = repo.lastUpdated,
        )
    }
}