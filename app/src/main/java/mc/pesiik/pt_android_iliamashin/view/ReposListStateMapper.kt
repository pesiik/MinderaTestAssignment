package mc.pesiik.pt_android_iliamashin.view

import mc.pesiik.pt_android_iliamashin.domain.Repo
import javax.inject.Inject

class ReposListStateMapper @Inject constructor() {

    fun mapDomainToUIState(
        domain: Result<List<Repo>>,
        previousState: ReposListState,
        isPaginating: Boolean,
    ): ReposListState {
        return when {
            domain.isSuccess -> {
                val repos = mapReposToUIList(domain.getOrThrow())
                val combined = if (isPaginating) {
                    previousState.repos + repos
                } else {
                    repos
                }
                previousState.copy(repos = combined, isIdle = false, errorMessage = null)
            }

            domain.isFailure -> mapToErrorState(domain)
            else -> throw IllegalStateException("Unreachable state")
        }
    }

    private fun mapReposToUIList(repos: List<Repo>): List<RepoUiModel> {
        return repos.map(this::mapRepoToUIItem)
    }

    private fun mapRepoToUIItem(repo: Repo): RepoUiModel {
        return RepoUiModel(
            id = repo.id,
            name = repo.name,
            ownerLogin = repo.ownerLogin,
            ownerAvatarUrl = repo.ownerAvatarUrl,
            description = repo.description.orEmpty(),
            starCount = repo.starsCount,
            language = repo.language,
        )
    }

    private fun mapToErrorState(domain: Result<List<Repo>>): ReposListState {
        val errorMessage = domain.exceptionOrNull()?.localizedMessage ?: "Unknown error"
        return ReposListState(errorMessage = errorMessage)
    }
}