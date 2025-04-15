package com.klu.musicplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.klu.musicplayer.databinding.FragmentPlaylistBinding

class PlaylistFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!
    private lateinit var songAdapter: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topSongs = listOf(
            Song("Shape of You", "Ed Sheeran", R.drawable.shape_of_you_cover, R.raw.shape_of_you),
            Song("Dance Monkey", "Tones and I", R.drawable.dance_monkey, R.raw.dance_monkey),
            Song("Someone You Loved", "Lewis Capaldi", R.drawable.someone_you_loved, R.raw.someone_you_loved)
        )

        songAdapter = SongAdapter(topSongs) { selectedSong ->
            // Open PlayerFragment and pass song details
            val playerFragment = PlayerFragment().apply {
                arguments = Bundle().apply {
                    putString("songTitle", selectedSong.title)
                    putString("songArtist", selectedSong.artist)
                    putInt("songImage", selectedSong.imageResId)
                    putInt("songFile", selectedSong.audioResId) // important
                    putBoolean("autoPlay", true) // 👈 new key to tell PlayerFragment to auto-play
                }
            }

            parentFragmentManager.commit {
                replace(R.id.nav_host_fragment, playerFragment)
                addToBackStack(null)
            }
        }

        binding.trackList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = songAdapter
        }

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
