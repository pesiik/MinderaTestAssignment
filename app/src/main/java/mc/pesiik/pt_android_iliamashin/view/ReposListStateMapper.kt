package mc.pesiik.pt_android_iliamashin.view

import mc.pesiik.pt_android_iliamashin.domain.Repo
import javax.inject.Inject

class ReposListStateMapper @Inject constructor() {

    fun mapDomainToUIState(
        domain: Result<List<Repo>>,
        previousState: ReposListState
    ): ReposListState {
        return when (previousState) {
            ReposListState.Loading -> handleState(domain)
            is ReposListState.Success -> handleState(domain = domain, previousSuccess = previousState)
            is ReposListState.Error -> handleState(domain)
        }
    }

    private fun handleState(
        domain: Result<List<Repo>>,
        previousSuccess: ReposListState.Success? = null,
    ): ReposListState {
        return when {
            domain.isSuccess -> {
                val repos = mapReposToUIList(domain.getOrThrow())
                previousSuccess?.copy(repos = repos) ?: ReposListState.Success(repos)
            }

            domain.isFailure -> mapToErrorState(domain)
            else -> ReposListState.Loading
        }
    }

    private fun mapReposToUIList(repos: List<Repo>): List<RepoUiModel> {
        return repos.map(this::mapRepoToUIItem)
    }

    private fun mapRepoToUIItem(repo: Repo): RepoUiModel {
        return RepoUiModel(
            name = repo.name,
            ownerAvatarUrl = repo.ownerAvatarUrl,
            description = repo.description.orEmpty(),
        )
    }

    private fun mapToErrorState(domain: Result<List<Repo>>): ReposListState {
        val errorMessage = domain.exceptionOrNull()?.localizedMessage ?: "Unknown error"
        return ReposListState.Error(message = errorMessage)
    }
}