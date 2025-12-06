package mc.pesiik.pt_android_iliamashin.view

import mc.pesiik.pt_android_iliamashin.domain.Repo
import javax.inject.Inject

class RepoDetailStateMapper @Inject constructor() {

    fun mapToUiState(repo: Repo): RepoDetailUiState {
        return RepoDetailUiState(
            name = repo.name,
            description = repo.description,
            forksCount = repo.forkCount,
            starsCount = repo.starCount,
            watchersCount = repo.watcherCount,
            lastUpdated = repo.lastUpdated,
        )
    }
}