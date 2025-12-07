package mc.pesiik.reposlistimpl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import mc.pesiik.reposlistapi.domain.ReposRepository
import mc.pesiik.reposlistimpl.data.ReposRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindReposRepository(
        impl: ReposRepositoryImpl
    ): ReposRepository
}