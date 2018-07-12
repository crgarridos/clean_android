package me.cgarrido.cleanandroid.ui.songs

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import me.cgarrido.cleanandroid.domain.repository.SongRepository
import me.cgarrido.cleanandroid.domain.test.factory.SongFactory
import me.cgarrido.cleanandroid.test.TestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SongsActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<SongsActivity>(SongsActivity::class.java, false, false)

    private val repo: SongRepository = TestApplication.appComponent.songRepository()

    @Test
    fun activityLaunches() {

        whenever(repo.getAll())
                .thenReturn(Single.just(SongFactory.makeList(10)))
        activity.launchActivity(null)
    }

}
