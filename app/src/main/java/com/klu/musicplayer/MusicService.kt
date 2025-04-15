package com.klu.musicplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MusicService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private var currentSongIndex = 0
    private var isShuffleEnabled = false
    private var isRepeatEnabled = false
    private var songList: List<String> = listOf()
    private val playlists: MutableMap<String, MutableList<String>> = mutableMapOf()
    private val favoriteSongs: MutableSet<String> = mutableSetOf()

    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun setSongList(songs: List<String>) {
        songList = songs
    }

    fun playSong(songPath: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(songPath)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    fun playCurrentSong() {
        if (songList.isNotEmpty()) {
            playSong(songList[currentSongIndex])
        }
    }

    fun playNextSong() {
        if (isShuffleEnabled) {
            currentSongIndex = (songList.indices).random()
        } else {
            currentSongIndex = (currentSongIndex + 1) % songList.size
        }
        playCurrentSong()
    }

    fun playPreviousSong() {
        if (isShuffleEnabled) {
            currentSongIndex = (songList.indices).random()
        } else {
            currentSongIndex = if (currentSongIndex - 1 < 0) songList.size - 1 else currentSongIndex - 1
        }
        playCurrentSong()
    }

    fun toggleShuffle() {
        isShuffleEnabled = !isShuffleEnabled
    }

    fun toggleRepeat() {
        isRepeatEnabled = !isRepeatEnabled
    }

    // --- PLAYLISTS FEATURES ---

    fun createPlaylist(name: String) {
        if (!playlists.containsKey(name)) {
            playlists[name] = mutableListOf()
        }
    }

    fun addSongToPlaylist(playlistName: String, song: String) {
        playlists[playlistName]?.add(song)
    }

    fun removeSongFromPlaylist(playlistName: String, song: String) {
        playlists[playlistName]?.remove(song)
    }

    fun getSongsInPlaylist(playlistName: String): List<String> {
        return playlists[playlistName]?.toList() ?: emptyList()
    }

    fun deletePlaylist(playlistName: String) {
        playlists.remove(playlistName)
    }

    fun getAllPlaylists(): Map<String, List<String>> {
        return playlists.mapValues { it.value.toList() }
    }

    // --- FAVORITES FEATURES ---

    fun addToFavorites(song: String) {
        favoriteSongs.add(song)
    }

    fun removeFromFavorites(song: String) {
        favoriteSongs.remove(song)
    }

    fun isFavorite(song: String): Boolean {
        return favoriteSongs.contains(song)
    }

    fun getAllFavorites(): List<String> {
        return favoriteSongs.toList()
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
