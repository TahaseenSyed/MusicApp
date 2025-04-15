package com.klu.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.klu.musicplayer.databinding.FragmentArtistBinding

class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var songAdapter: SongAdapter
    private val songList = mutableListOf<Song>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val artistName = requireArguments().getString("artist_name", "Unknown Artist")
        val artistImage = requireArguments().getInt("artist_image", R.drawable.artist_2)

        binding.artistName.text = artistName
        binding.artistImage.setImageResource(artistImage)

        songList.addAll(getPopularSongs(artistName))

        // Setup RecyclerView
        songAdapter = SongAdapter(songList) { song -> playSong(song) }
        binding.songRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.songRecyclerView.adapter = songAdapter

        // Play first song when play button is clicked
        binding.playButton.setOnClickListener {
            if (songList.isNotEmpty()) {
                playSong(songList.first())
            } else {
                Toast.makeText(requireContext(), "No songs available", Toast.LENGTH_SHORT).show()
            }
        }

        // Show Add Song dialog
        binding.btnAddSong.setOnClickListener {
            showAddSongDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
        _binding = null
    }

    private fun getPopularSongs(artist: String): List<Song> {
        return when (artist) {
            "Taylor Swift" -> listOf(
                Song("Love Story", "Taylor Swift", R.drawable.love_story_cover, R.raw.love_story),
                Song("Shake It Off", "Taylor Swift", R.drawable.shake_it_off_cover, R.raw.shake_it_off),
                Song("Blank Space", "Taylor Swift", R.drawable.blank_space_cover, R.raw.blank_space)
            )
            "Ed Sheeran" -> listOf(
                Song("Shape of You", "Ed Sheeran", R.drawable.shape_of_you_cover, R.raw.shape_of_you),
                Song("Perfect", "Ed Sheeran", R.drawable.perfect, R.raw.perfect),
                Song("Thinking Out Loud", "Ed Sheeran", R.drawable.thinking_out_loud_cover, R.raw.thinking_out_loud)
            )
            else -> listOf(
                Song("Unknown Song", "Unknown Artist", R.drawable.song_image1, R.raw.song_1)
            )
        }
    }

    private fun playSong(song: Song) {
        mediaPlayer?.release()
        mediaPlayer = null

        mediaPlayer = MediaPlayer.create(requireContext(), song.audioResId)
        mediaPlayer?.apply {
            start()
            setOnCompletionListener {
                release()
                mediaPlayer = null
            }
        }
    }

    private fun showAddSongDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_song, null)
        val editSongTitle = dialogView.findViewById<EditText>(R.id.editSongTitle)

        AlertDialog.Builder(requireContext())
            .setTitle("Add New Song")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val newSongTitle = editSongTitle.text.toString().trim()
                if (newSongTitle.isNotEmpty()) {
                    val newSong = Song(newSongTitle, "Unknown Artist", R.drawable.song_image1, R.raw.song_1)
                    songList.add(newSong)
                    songAdapter.notifyItemInserted(songList.size - 1)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
