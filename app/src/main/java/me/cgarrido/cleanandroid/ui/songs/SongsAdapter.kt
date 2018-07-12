package me.cgarrido.cleanandroid.ui.songs

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_song.view.*
import me.cgarrido.cleanandroid.R
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.utils.inflate

class SongsAdapter(private var songs: List<Song>, private val picasso: Picasso) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    override fun getItemCount(): Int = songs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(parent.inflate(R.layout.item_song), picasso)
    }


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songs[holder.adapterPosition])
    }

    class SongViewHolder(view: View, private val picasso: Picasso) : RecyclerView.ViewHolder(view) {
        fun bind(song: Song) {
            itemView.titleTextView.text = song.title
            picasso.load(song.thumbnailUrl)
                    .placeholder(android.R.drawable.stat_sys_download)
                    .into(itemView.thumbnailImageView)

        }
    }
}

