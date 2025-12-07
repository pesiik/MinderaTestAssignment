package mc.pesiik.repodetailsimpl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import mc.pesiik.repodetailsimpl.data.GitRepoDetailService
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    fun provideRepoDetailApiService(retrofit: Retrofit): GitRepoDetailService {
        return retrofit.create(GitRepoDetailService::class.java)
    }
}