package mc.pesiik.reposlistimpl.view

import mc.pesiik.reposlistapi.domain.Repo
import javax.inject.Inject

internal class ReposListStateMapper @Inject constructor() {

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
                previousState.copy(repos = combined, errorMessage = null)
            }

            domain.isFailure -> mapToErrorState(domain, previousState)
            else -> throw IllegalStateException("Unreachable state")
        }
    }

    fun shimmerRepoModels(
        count: Int,
        previousState: ReposListState,
        isPaginating: Boolean,
    ): List<RepoUiModel> {
        val previous = previousState.repos.takeIf { isPaginating }.orEmpty()
        return previous + List(count) { index -> RepoUiModel(id = index, isShimmer = true) }
    }

    private fun mapReposToUIList(repos: List<Repo>): List<RepoUiModel> {
        return repos.map(this::mapRepoToUIItem)
    }

    private fun mapRepoToUIItem(repo: Repo): RepoUiModel {
        return RepoUiModel(
            id = repo.id,
            name = repo.name,
            ownerLogin = repo.user.login,
            ownerAvatarUrl = repo.user.avatarUrl,
            description = repo.description.orEmpty(),
            starCount = repo.starsCount,
            language = repo.language,
            isShimmer = false,
        )
    }

    private fun mapToErrorState(domain: Result<List<Repo>>, previousState: ReposListState): ReposListState {
        val errorMessage = domain.exceptionOrNull()?.localizedMessage ?: "Unknown error"
        return previousState.copy(
            errorMessage = errorMessage,
        )
    }
}