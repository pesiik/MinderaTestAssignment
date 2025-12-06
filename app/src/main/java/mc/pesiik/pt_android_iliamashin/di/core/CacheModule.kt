package mc.pesiik.pt_android_iliamashin.di.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mc.pesiik.pt_android_iliamashin.core.navigation.cache.ReposTemporaryCache

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    fun provideCache(): ReposTemporaryCache {
        return ReposTemporaryCache()
    }
}