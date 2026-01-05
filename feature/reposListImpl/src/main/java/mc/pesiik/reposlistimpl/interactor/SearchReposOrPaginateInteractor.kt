package mc.pesiik.reposlistimpl.interactor

import mc.pesiik.reposlistapi.domain.Repo
import mc.pesiik.reposlistapi.domain.ReposRepository
import javax.inject.Inject

class SearchReposOrPaginateInteractor @Inject constructor(
    private val reposRepository: ReposRepository
) {
    suspend fun searchRepos(
        query: String,
        page: Int,
        sort: String?,
    ): List<Repo> {
        return reposRepository.searchRepos(
            query = query,
            perPage = PER_PAGE_COUNT,
            page = page,
            sort = sort
        )
    }

    fun hasToPaginate(lastVisiblePosition: Int, currentPage: Int): Boolean {
        val lastIndex = PER_PAGE_COUNT * currentPage - START_PAGINATION_STEP
        return lastVisiblePosition >= lastIndex
    }

    companion object {
        private const val PER_PAGE_COUNT = 30
        private const val START_PAGINATION_STEP = 15
    }
}