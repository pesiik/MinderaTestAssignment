package mc.pesiik.data.cache.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mc.pesiik.data.cache.TemporaryCache
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideCache(): TemporaryCache {
        return TemporaryCache()
    }
}