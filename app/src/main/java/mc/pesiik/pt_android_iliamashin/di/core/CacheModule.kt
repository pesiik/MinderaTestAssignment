package mc.pesiik.pt_android_iliamashin.di.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mc.pesiik.pt_android_iliamashin.core.navigation.cache.ReposTemporaryCache
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideCache(): ReposTemporaryCache {
        return ReposTemporaryCache()
    }
}