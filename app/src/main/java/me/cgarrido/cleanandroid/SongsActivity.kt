package me.cgarrido.cleanandroid

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import me.cgarrido.cleanandroid.base.BaseActivity
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.viewmodel.SongsViewModel
import me.cgarrido.cleanandroid.viewmodel.livedata.observe
import me.cgarrido.cleanandroid.viewmodel.resources.Result
import javax.inject.Inject

class SongsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SongsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SongsViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()
        viewModel.retrieveSongs()
        viewModel.songs.observe(this, ::handleSongsResult)
    }


    private fun handleSongsResult(result: Result<List<Song>>) {
        textView.text = when (result) {
            is Result.Success -> result.data.take(3).toString()
            is Result.Error -> result.throwable.localizedMessage
            is Result.Loading -> "Loading..."
        }
    }

}
