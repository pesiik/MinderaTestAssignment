package mc.pesiik.repodetailsimpl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import mc.pesiik.repodetailsapi.domain.RepoDetailsRepository
import mc.pesiik.repodetailsimpl.data.RepoDetailsRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface DomainModule {

    @Binds
    fun bindRepoDetailsRepository(
        impl: RepoDetailsRepositoryImpl
    ): RepoDetailsRepository
}