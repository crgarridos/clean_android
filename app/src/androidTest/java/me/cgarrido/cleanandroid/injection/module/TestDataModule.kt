package me.cgarrido.cleanandroid.injection.module

import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import me.cgarrido.cleanandroid.domain.repository.SongRepository
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideSongRepository(): SongRepository = mock()
}
