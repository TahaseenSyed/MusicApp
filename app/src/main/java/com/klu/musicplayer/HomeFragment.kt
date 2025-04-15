package com.klu.musicplayer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.klu.musicplayer.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupNavigation()
    }

    private fun setupRecyclerViews() {
        val artists = listOf(
            Artist("Taylor Swift", R.drawable.artist_2),
            Artist("The Weeknd", R.drawable.artist_1),
            Artist("Ed Sheeran", R.drawable.artist_4),
            Artist("Adele", R.drawable.adele),
            Artist("Drake", R.drawable.drake)
        )

        val trendingSongs = listOf(
            Song("Blinding Lights", "The Weeknd", R.drawable.artist_1, R.raw.song_6),
            Song("Levitating", "Dua Lipa", R.drawable.artist_2, R.raw.song_3),
            Song("Peaches", "Justin Bieber", R.drawable.artist_3, R.raw.song_2)
        )

        val recentlyPlayedSongs = listOf(
            Song("Shape of You", "Ed Sheeran", R.drawable.artist_4, R.raw.song_4),
            Song("Stay", "The Kid LAROI & Justin Bieber", R.drawable.artist_5, R.raw.song_3),
            Song("Save Your Tears", "The Weeknd", R.drawable.artist_1, R.raw.song_5)
        )

        setupArtistsRecyclerView(artists)
        setupSongsRecyclerView(trendingSongs, recentlyPlayedSongs)
    }

    private fun setupArtistsRecyclerView(artists: List<Artist>) {
        binding.recyclerViewArtists.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = ArtistAdapter(artists) { selectedArtist ->
                val bundle = Bundle().apply {
                    putString("artist_name", selectedArtist.name)
                    putInt("artist_image", selectedArtist.imageResId)
                }
                findNavController().navigate(R.id.action_homeFragment_to_artistFragment, bundle)
            }
        }
    }

    private fun setupSongsRecyclerView(trendingSongs: List<Song>, recentlyPlayedSongs: List<Song>) {
        val songClickListener: (Song) -> Unit = { selectedSong ->
            val bundle = Bundle().apply {
                putString("songTitle", selectedSong.title)
                putString("songArtist", selectedSong.artist)
                putInt("songImage", selectedSong.imageResId)
                putInt("songFile", selectedSong.audioResId)
            }
            findNavController().navigate(R.id.action_homeFragment_to_playerFragment, bundle)
        }

        binding.recyclerViewTrending.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = SongAdapter(trendingSongs, songClickListener)
        }

        binding.recyclerViewRecentlyPlayed.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = SongAdapter(recentlyPlayedSongs, songClickListener)
        }
    }

    private fun setupNavigation() {
        binding.notificationIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notifications)
        }

        binding.imgProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
