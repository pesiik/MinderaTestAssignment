package mc.pesiik.pt_android_iliamashin.di.feature.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import mc.pesiik.pt_android_iliamashin.data.RepoDetailsRepositoryImpl
import mc.pesiik.pt_android_iliamashin.domain.RepoDetailsRepository

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindRepoDetailsRepository(
        impl: RepoDetailsRepositoryImpl
    ): RepoDetailsRepository
}