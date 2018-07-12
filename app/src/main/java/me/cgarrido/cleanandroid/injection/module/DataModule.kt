package me.cgarrido.cleanandroid.injection.module

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import me.cgarrido.cleanandroid.data.BuildConfig
import me.cgarrido.cleanandroid.data.SongCacheSource
import me.cgarrido.cleanandroid.data.SongRemoteSource
import me.cgarrido.cleanandroid.data.SongRepositoryImpl
import me.cgarrido.cleanandroid.data.cache.AppPreferences
import me.cgarrido.cleanandroid.data.cache.SongCacheSourceImpl
import me.cgarrido.cleanandroid.data.cache.database.AppDatabase
import me.cgarrido.cleanandroid.data.remote.SongRemoteSourceImpl
import me.cgarrido.cleanandroid.data.remote.service.ServiceFactory
import me.cgarrido.cleanandroid.data.remote.service.SongService
import me.cgarrido.cleanandroid.domain.repository.SongRepository

@Module
interface DataModule {
    @Binds
    fun bindSongRepository(repo: SongRepositoryImpl): SongRepository

    @Binds
    fun bindSongCacheSource(repo: SongCacheSourceImpl): SongCacheSource

    @Binds
    fun bindSongRemoteSource(repo: SongRemoteSourceImpl): SongRemoteSource

    @Module
    companion object {
        @Provides
        fun provideDatabase(context: Context): AppDatabase {
            return AppDatabase.getInstance(context)
        }

        @Provides
        fun providePreferences(context: Context): AppPreferences {
            return AppPreferences(context)
        }

        @Provides
        fun provideSongService(): SongService {
            return ServiceFactory.makeSongService(BuildConfig.DEBUG)
        }
    }
}