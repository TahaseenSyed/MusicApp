package com.klu.musicplayer

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class TrendingSongsFragment : Fragment(R.layout.fragment_trending_songs) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedSong = arguments?.getString("selectedSong")
        val songTitleTextView: TextView = view.findViewById(R.id.songTitleTextView)
        songTitleTextView.text = selectedSong ?: "Trending Songs"
    }
}
