package com.klu.musicplayer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.klu.musicplayer.databinding.ActivityPlaylistDetailBinding

class PlaylistDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playlist: Playlist? = intent.getParcelableExtra("playlist")

        if (playlist != null) {
            binding.playlistName.text = playlist.name
            binding.playlistImage.setImageResource(playlist.imageResId)

            // Setup RecyclerView for songs in the playlist
            binding.recyclerViewSongs.apply {
                layoutManager = LinearLayoutManager(this@PlaylistDetailActivity)
                adapter = SongAdapter(playlist.songs) { selectedSong ->
                    // Open PlayerActivity when a song is clicked
                    val intent = Intent(this@PlaylistDetailActivity, PlayerActivity::class.java).apply {
                        putExtra("selectedSong", selectedSong)
                    }
                    startActivity(intent)
                }
            }
        } else {
            finish() // Close activity if no playlist is found
        }
    }
}
