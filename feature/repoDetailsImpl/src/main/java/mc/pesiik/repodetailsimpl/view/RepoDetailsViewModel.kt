package mc.pesiik.repodetailsimpl.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mc.pesiik.repodetailsapi.domain.RepoDetailsRepository

@HiltViewModel(assistedFactory = RepoDetailsViewModel.Factory::class)
internal class RepoDetailsViewModel @AssistedInject constructor(
    @Assisted private val repoId: Int,
    private val repoDetailsRepository: RepoDetailsRepository,
    private val repoDetailStateMapper: RepoDetailStateMapper,
) : ViewModel() {


    private val _state = MutableStateFlow(RepoDetailUiState())
    val state: StateFlow<RepoDetailUiState> = _state.asStateFlow()

    init {
        loadRepoDetails()
    }

    private fun loadRepoDetails() {
        // todo handle errors
        viewModelScope.launch {
            val repo = repoDetailsRepository.getRepoDetails(repoId)
            val uiState = repoDetailStateMapper.mapToUiState(repo)
            _state.value = uiState
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(repoId: Int): RepoDetailsViewModel
    }
}