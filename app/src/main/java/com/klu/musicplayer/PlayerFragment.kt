package com.klu.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.klu.musicplayer.databinding.FragmentPlayerBinding
import kotlin.random.Random

class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private var mediaPlayer: MediaPlayer? = null

    private var currentSongIndex = 0
    private var isShuffle = false
    private var isRepeat = false

    private val songList = mutableListOf(
        Song("Sun Down", "Gordon Lightfoot", R.drawable.sample_album, R.raw.song_1),
        Song("Skyfall", "Adele", R.drawable.album_cover_2, R.raw.song_2),
        Song("Perfect", "Ed Sheeran", R.drawable.perfect, R.raw.song_3),
        Song("Dance Monkey", "Tones and I", R.drawable.dance_monkey, R.raw.dance_monkey),
        Song("Someone You Loved", "Lewis Capaldi", R.drawable.someone_you_loved, R.raw.someone_you_loved)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get song data from arguments (if passed from HomeFragment or PlaylistFragment)
        val songTitle = arguments?.getString("songTitle")
        val songArtist = arguments?.getString("songArtist")
        val songImage = arguments?.getInt("songImage", -1) ?: -1
        val songFile = arguments?.getInt("songFile", -1) ?: -1

        if (!songTitle.isNullOrEmpty() && !songArtist.isNullOrEmpty() && songImage != -1 && songFile != -1) {
            songList.add(Song(songTitle, songArtist, songImage, songFile))
            currentSongIndex = songList.size - 1
        }

        updateSongUI()

        // Play/Pause button functionality
        binding.btnPlayPause.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                pauseMusic()
            } else {
                playMusic()
            }
        }

        // Next Song button functionality
        binding.btnNext.setOnClickListener { nextSong() }

        // Previous Song button functionality
        binding.btnPrevious.setOnClickListener { prevSong() }

        // Shuffle button functionality
        binding.btnShuffle.setOnClickListener {
            isShuffle = !isShuffle
            updateButtonColor(binding.btnShuffle, isShuffle)
        }

        // Repeat button functionality
        binding.btnRepeat.setOnClickListener {
            isRepeat = !isRepeat
            updateButtonColor(binding.btnRepeat, isRepeat)
        }

        // Back button functionality to navigate back to PlaylistFragment
        binding.icBack.setOnClickListener {
            parentFragmentManager.popBackStack() // This will pop the PlayerFragment from the back stack and go back to the previous fragment
        }
    }

    private fun updateSongUI() {
        val currentSong = songList[currentSongIndex]
        binding.albumCover.setImageResource(currentSong.imageResId)
        binding.songTitle.text = currentSong.title
        binding.artistName.text = currentSong.artist
    }

    private fun playMusic() {
        val currentSong = songList[currentSongIndex]

        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(requireContext(), currentSong.audioResId).apply {
            setOnCompletionListener {
                if (isRepeat) {
                    playMusic() // Loop the song
                } else {
                    nextSong() // Move to the next song
                }
            }
            start()
        }

        binding.btnPlayPause.setImageResource(R.drawable.ic_pause) // Change icon to pause
    }

    private fun pauseMusic() {
        mediaPlayer?.pause()
        binding.btnPlayPause.setImageResource(R.drawable.ic_play) // Change icon to play
    }

    private fun nextSong() {
        currentSongIndex = if (isShuffle) {
            Random.nextInt(songList.size) // Randomly select a song if shuffle is enabled
        } else {
            (currentSongIndex + 1) % songList.size // Go to the next song in the list
        }
        updateSongUI()
        playMusic()
    }

    private fun prevSong() {
        currentSongIndex = if (currentSongIndex > 0) {
            currentSongIndex - 1 // Go to previous song
        } else {
            songList.size - 1 // Wrap around to the last song
        }
        updateSongUI()
        playMusic()
    }

    private fun updateButtonColor(button: View, isActive: Boolean) {
        button.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                if (isActive) android.R.color.holo_green_light else android.R.color.white
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
        _binding = null
    }
}
