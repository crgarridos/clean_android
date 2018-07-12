package me.cgarrido.cleanandroid.ui.songs

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import me.cgarrido.cleanandroid.R
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.domain.repository.SongRepository
import me.cgarrido.cleanandroid.domain.test.factory.SongFactory
import me.cgarrido.cleanandroid.test.TestApplication
import me.cgarrido.cleanandroid.test.matcher.RecyclerViewMatcher.Companion.withRecyclerView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SongsActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<SongsActivity>(SongsActivity::class.java, false, false)

    private val repo: SongRepository = TestApplication.appComponent.songRepository()
    private val songs = SongFactory.makeList(5)

    @Before
    fun setup() {
        whenever(repo.getAll())
                .thenReturn(Single.just(songs))
    }

    @Test
    fun activityLaunches() {
        activity.launchActivity(null)
    }

    @Test
    fun songsDisplayed() {
        activity.launchActivity(null)

        checkSongItemIsDisplayed(songs[0], 0)
        checkSongItemIsDisplayed(songs[1], 1)
    }

    @Test
    fun scrollOnSongsList() {
        activity.launchActivity(null)

        for (i in songs.indices) {
            scrollToPosition(i)
            checkSongItemIsDisplayed(songs[i], i)
        }
    }

    private fun checkSongItemIsDisplayed(song: Song, atPosition: Int) {
        onView(withRecyclerView(R.id.songsRecyclerView).atPosition(atPosition))
                .check(matches(hasDescendant(withText(song.title))))

    }
    private fun scrollToPosition(position: Int){
        onView(withId(R.id.songsRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
    }
}
