package mc.pesiik.pt_android_iliamashin.di.feature

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import mc.pesiik.pt_android_iliamashin.data.GitRepoDetailService
import mc.pesiik.pt_android_iliamashin.data.GitReposService
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {
    @Provides
    fun provideRepoApiService(retrofit: Retrofit): GitReposService {
        return retrofit.create(GitReposService::class.java)
    }

    @Provides
    fun provideRepoDetailApiService(retrofit: Retrofit): GitRepoDetailService {
        return retrofit.create(GitRepoDetailService::class.java)
    }
}