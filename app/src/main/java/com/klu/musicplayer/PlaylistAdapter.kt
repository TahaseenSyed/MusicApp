package com.klu.musicplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.klu.musicplayer.databinding.ItemPlaylistBinding

class PlaylistAdapter(
    private val playlists: List<Playlist>,
    private val onPlaylistClick: (Playlist) -> Unit,
    private val onCreatePlaylist: () -> Unit,
    private val onDeletePlaylist: (Playlist) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    inner class PlaylistViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(playlist: Playlist) {
            binding.playlistName.text = playlist.name
            binding.playlistImage.setImageResource(playlist.imageResId)

            // Click listener for opening playlist details
            binding.root.setOnClickListener { onPlaylistClick(playlist) }

            // Show three-dot menu options
            binding.moreOptions.setOnClickListener { view ->
                showPopupMenu(view, playlist)
            }
        }

        private fun showPopupMenu(view: View, playlist: Playlist) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.playlist_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.create_playlist -> {
                        onCreatePlaylist()
                        true
                    }
                    R.id.delete_playlist -> {
                        onDeletePlaylist(playlist)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding = ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position])
    }

    override fun getItemCount(): Int = playlists.size
}
