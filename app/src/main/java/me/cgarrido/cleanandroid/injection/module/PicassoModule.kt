package me.cgarrido.cleanandroid.injection.module

import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object PicassoModule {

    @Provides
    @JvmStatic
    @Singleton
    fun providePicasso(context: Context): Picasso {
        return Picasso.Builder(context).build()
    }
}