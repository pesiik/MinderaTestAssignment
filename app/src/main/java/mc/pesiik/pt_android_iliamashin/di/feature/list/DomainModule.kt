package mc.pesiik.pt_android_iliamashin.di.feature.list

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import mc.pesiik.pt_android_iliamashin.data.ReposRepositoryImpl
import mc.pesiik.pt_android_iliamashin.domain.ReposRepository

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindReposRepository(
        impl: ReposRepositoryImpl
    ): ReposRepository
}