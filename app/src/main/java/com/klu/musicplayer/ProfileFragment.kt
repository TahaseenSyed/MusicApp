package com.klu.musicplayer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var songAdapter: SongAdapter
    private val songList = mutableListOf<Song>()  // changed to val as list reference doesn't change

    private lateinit var editProfileButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        setupRecyclerView(view)
        setupEditProfileButton(view)

        loadSongs()

        return view
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view_top_played)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        songAdapter = SongAdapter(songList) { song -> onSongClicked(song) }
        recyclerView.adapter = songAdapter
    }

    private fun setupEditProfileButton(view: View) {
        editProfileButton = view.findViewById(R.id.buttonEditProfile)
        editProfileButton.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadSongs() {
        songList.apply {
            clear()
            add(Song("Blinding Lights", "The Weeknd", R.drawable.artist_1, R.raw.blinding_lights_audio))
            add(Song("Levitating", "Dua Lipa", R.drawable.artist_2, R.raw.levitating_audio))
            add(Song("Watermelon Sugar", "Harry Styles", R.drawable.harry_styles, R.raw.watermelon_sugar_audio))
        }
        songAdapter.notifyDataSetChanged()
    }

    private fun onSongClicked(song: Song) {
        Toast.makeText(requireContext(), "Clicked: ${song.title} by ${song.artist}", Toast.LENGTH_SHORT).show()
    }
}
