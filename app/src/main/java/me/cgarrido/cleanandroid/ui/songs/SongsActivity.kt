package me.cgarrido.cleanandroid.ui.songs

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import me.cgarrido.cleanandroid.R
import me.cgarrido.cleanandroid.base.BaseActivity
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.utils.visible
import me.cgarrido.cleanandroid.viewmodel.SongsViewModel
import me.cgarrido.cleanandroid.viewmodel.livedata.observe
import me.cgarrido.cleanandroid.viewmodel.resources.Result
import javax.inject.Inject

class SongsActivity : BaseActivity() {

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SongsViewModel
    private lateinit var songsAdapter: SongsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        songsAdapter = SongsAdapter(picasso)
        songsRecyclerView.adapter = songsAdapter

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SongsViewModel::class.java)

        if (savedInstanceState == null)
            viewModel.retrieveSongs()

        viewModel.songs.observe(this, ::handleSongsResult)

    }


    private fun handleSongsResult(result: Result<PagedList<Song>>) {
        when (result) {
            is Result.Success -> refreshSongList(result.data)
            is Result.Error -> showError(result.throwable)
            is Result.Loading -> {
                songsRecyclerView.visible = false
                progressBar.visible = true
            }
        }
    }

    private fun refreshSongList(songs: PagedList<Song>) {
        songsAdapter.submitList(songs)
        progressBar.visible = false
        songsRecyclerView.visible = true
    }

    private fun showError(throwable: Throwable) {
        snackbar(throwable.localizedMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok) {}
                .show()
    }


}
