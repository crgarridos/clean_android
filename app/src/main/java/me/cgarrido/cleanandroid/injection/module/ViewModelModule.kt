package me.cgarrido.cleanandroid.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import me.cgarrido.cleanandroid.injection.CleanAndroidViewModelFactory
import me.cgarrido.cleanandroid.viewmodel.SongsViewModel
import kotlin.reflect.KClass


@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SongsViewModel::class)
    fun bindBrowseProjectsViewModel(viewModel: SongsViewModel): ViewModel


    @Binds
    fun bindViewModelFactory(factory: CleanAndroidViewModelFactory): ViewModelProvider.Factory
}
