package mc.pesiik.reposlistimpl.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mc.pesiik.reposlistapi.domain.Repo
import mc.pesiik.reposlistapi.domain.ReposRepository
import mc.pesiik.viewmodel.launchCatching
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ReposListViewModel @Inject constructor(
    private val reposRepository: ReposRepository,
    private val reposListStateMapper: ReposListStateMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(ReposListState(isIdle = true))
    val state: StateFlow<ReposListState> = _state.asStateFlow()

    private val searchQueryFlow = MutableStateFlow("")
    private val pageMutableState = MutableStateFlow(1)

    init {
        viewModelScope.launch {
            searchQueryFlow
                .debounce(DEBOUNCE_MILLIS)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest { query ->
                    resetPagination()
                    performSearch(query)
                }
        }
        viewModelScope.launch {
            pageMutableState
                .filter { it > 1 }
                .collectLatest { page ->
                    val currentQuery = _state.value.searchQuery
                    if (currentQuery.isNotBlank() && page > 1) {
                        performSearch(currentQuery, page)
                    }
                }
        }
    }

    fun onEvent(event: ReposListScreenEvent) {
        when (event) {
            is ReposListScreenEvent.SearchRepos -> tryToSearch(event.query)
            is ReposListScreenEvent.ToggleSearchMode -> toggleSearchMode(event.isInSearchMode)
            is ReposListScreenEvent.ScrollReposList -> tryToLoadNextPage(event.lastVisiblePosition)
            is ReposListScreenEvent.SortClicked -> sortMenu(show = true)
            is ReposListScreenEvent.SortedByOptionSelected -> sortReposList(sortOption = event.sortOption)
            is ReposListScreenEvent.BackButtonClicked -> backButtonClicked()
        }
    }

    private fun tryToSearch(query: String) {
        val currentState = _state.value
        _state.value = currentState.copy(
            searchQuery = query
        )
        searchQueryFlow.value = query
    }

    private fun performSearch(query: String, page: Int = 1) {
        updateReposListWithShimmers(page)
        val sortOption = _state.value.selectedSortOption
        launchCatching(
            block = {
                reposRepository.searchRepos(
                    query = query,
                    perPage = PER_PAGE_COUNT,
                    page = page,
                    sort = sortOption.value
                )
            },
            onComplete = { data ->
                updateReposList(data, page)
            }
        )
    }

    private fun updateReposList(data: Result<List<Repo>>, page: Int) {
        val newState = reposListStateMapper.mapDomainToUIState(
            domain = data,
            previousState = _state.value,
            isPaginating = page > 1,
        )
        _state.value = newState
    }

    private fun updateReposListWithShimmers(page: Int) {
        val shimmeredState = reposListStateMapper.shimmerRepoModels(
            count = SHIMMERS_COUNT,
            previousState = _state.value,
            isPaginating = page > 1,
        )
        _state.update {
            it.copy(
                repos = shimmeredState
            )
        }
    }

    private fun toggleSearchMode(isInSearchMode: Boolean) {
        _state.update {
            it.copy(
                isInSearchMode = isInSearchMode
            )
        }
    }

    private fun tryToLoadNextPage(lastVisiblePosition: Int) {
        val lastIndex = PER_PAGE_COUNT * pageMutableState.value - START_PAGINATION_STEP
        val hasToPaginate = lastVisiblePosition >= lastIndex
        if (hasToPaginate) {
            pageMutableState.update { currentPage ->
                currentPage + 1
            }
        }
    }

    private fun resetPagination() {
        pageMutableState.value = 1
    }

    private fun sortMenu(show: Boolean) {
        _state.update {
            it.copy(
                isSortMenuOpened = show
            )
        }
    }

    private fun sortReposList(sortOption: ReposSortOption) {
        sortMenu(show = false)
        _state.update {
            it.copy(
                selectedSortOption = sortOption
            )
        }
        val currentQuery = _state.value.searchQuery
        if (currentQuery.isNotBlank()) {
            resetPagination()
            performSearch(currentQuery)
        }
    }

    private fun backButtonClicked() {
        val isInSearchMode = _state.value.isInSearchMode
        if (isInSearchMode) {
            toggleSearchMode(isInSearchMode = false)
        } else {
            _state.update {
                ReposListState(shouldCloseApp = true)
            }
        }
    }

    companion object {
        private const val DEBOUNCE_MILLIS = 500L
        private const val PER_PAGE_COUNT = 30
        private const val START_PAGINATION_STEP = 15
        private const val SHIMMERS_COUNT = 5
    }
}