package mc.pesiik.pt_android_iliamashin.view

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import mc.pesiik.pt_android_iliamashin.domain.RepoDetailsRepository

@HiltViewModel(assistedFactory = RepoDetailsViewModel.Factory::class)
class RepoDetailsViewModel @AssistedInject constructor(
    @Assisted private val repoId: Int,
    private val repoDetailsRepository: RepoDetailsRepository
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(repoId: Int): RepoDetailsViewModel
    }
}