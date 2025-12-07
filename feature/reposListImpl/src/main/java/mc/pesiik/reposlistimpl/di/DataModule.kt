package mc.pesiik.reposlistimpl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import mc.pesiik.reposlistimpl.data.GitReposService
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {
    @Provides
    fun provideRepoApiService(retrofit: Retrofit): GitReposService {
        return retrofit.create(GitReposService::class.java)
    }
}