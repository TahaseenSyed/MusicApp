package com.klu.musicplayer

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class RecentlyPlayedFragment : Fragment(R.layout.fragment_recently_played) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedSong = arguments?.getString("selectedSong")
        val recentlyPlayedTextView: TextView = view.findViewById(R.id.recentlyPlayedTextView)
        recentlyPlayedTextView.text = selectedSong ?: "Recently Played Songs"
    }
}
