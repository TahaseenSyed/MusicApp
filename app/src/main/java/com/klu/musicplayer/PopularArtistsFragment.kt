package com.klu.musicplayer

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class PopularArtistsFragment : Fragment(R.layout.fragment_popular_artists) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedArtist = arguments?.getString("selectedArtist")
        val artistNameTextView: TextView = view.findViewById(R.id.artistNameTextView)
        artistNameTextView.text = selectedArtist ?: "Popular Artists"
    }
}
