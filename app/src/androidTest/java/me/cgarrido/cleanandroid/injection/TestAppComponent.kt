package me.cgarrido.cleanandroid.injection

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.cgarrido.cleanandroid.domain.repository.SongRepository
import me.cgarrido.cleanandroid.injection.module.*
import me.cgarrido.cleanandroid.test.TestApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    ViewModelModule::class,
    PicassoModule::class,
    TestAppModule::class,
    TestDataModule::class
])
interface TestAppComponent : AndroidInjector<TestApplication> {

    fun songRepository(): SongRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: TestApplication): TestAppComponent.Builder

        fun build(): TestAppComponent
    }

    override fun inject(application: TestApplication)
    fun injectTest(target: Any)

}


