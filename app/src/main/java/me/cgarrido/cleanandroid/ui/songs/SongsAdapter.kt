package me.cgarrido.cleanandroid.ui.songs

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_song.view.*
import me.cgarrido.cleanandroid.R
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.utils.inflate

class SongsAdapter(private val picasso: Picasso) : PagedListAdapter<Song, SongsAdapter.SongViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(parent.inflate(R.layout.item_song), picasso)
    }


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition) ?: return)
    }

    class SongViewHolder(view: View, private val picasso: Picasso) : RecyclerView.ViewHolder(view) {
        fun bind(song: Song) {
            itemView.titleTextView.text = song.title
            picasso.load(song.thumbnailUrl)
                    .placeholder(android.R.drawable.stat_sys_download)
                    .into(itemView.thumbnailImageView)

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Song>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldConcert: Song,
                                         newConcert: Song): Boolean =
                    oldConcert.id == newConcert.id

            override fun areContentsTheSame(oldConcert: Song,
                                            newConcert: Song): Boolean =
                    oldConcert == newConcert
        }
    }
}

