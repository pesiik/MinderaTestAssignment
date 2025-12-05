package mc.pesiik.pt_android_iliamashin.view

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mc.pesiik.pt_android_iliamashin.core.launchCatching
import mc.pesiik.pt_android_iliamashin.domain.Repo
import mc.pesiik.pt_android_iliamashin.domain.ReposRepository
import javax.inject.Inject

@HiltViewModel
class ReposListViewModel @Inject constructor(
    private val reposRepository: ReposRepository,
    private val reposListStateMapper: ReposListStateMapper,
) : ViewModel() {

    private val _state = MutableStateFlow<ReposListState>(ReposListState.Loading)
    val state: StateFlow<ReposListState> = _state.asStateFlow()

    fun onEvent(event: ReposListScreenEvent) {
        when (event) {
            is ReposListScreenEvent.SearchRepos -> searchRepos(event.organization)
            is ReposListScreenEvent.SortClicked -> Unit // todo
        }
    }

    private fun searchRepos(organization: String) {
        launchCatching(
            block = {
                reposRepository.searchRepos(
                    organization = organization
                )
            },
            onComplete = { data ->
                updateReposList(data)
            }
        )
    }

    private fun updateReposList(data: Result<List<Repo>>) {
        val newState = reposListStateMapper.mapDomainToUIState(
            domain = data,
            previousState = _state.value
        )
        _state.value = newState
    }
}