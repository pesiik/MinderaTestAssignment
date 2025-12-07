package mc.pesiik.reposlistimpl.view

import mc.pesiik.reposlistapi.domain.Repo
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
                }.filter { !it.isShimmer }
                previousState.copy(repos = combined, isIdle = false, errorMessage = null)
            }

            domain.isFailure -> mapToErrorState(domain)
            else -> throw IllegalStateException("Unreachable state")
        }
    }

    fun shimmerRepoModels(
        count: Int,
        previousState: ReposListState,
        isPaginating: Boolean,
    ): List<RepoUiModel> {
        val previous = previousState.repos.takeIf { isPaginating }.orEmpty()
        return previous + List(count) { RepoUiModel(isShimmer = true) }
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
            isShimmer = false,
        )
    }

    private fun mapToErrorState(domain: Result<List<Repo>>): ReposListState {
        val errorMessage = domain.exceptionOrNull()?.localizedMessage ?: "Unknown error"
        return ReposListState(errorMessage = errorMessage)
    }
}