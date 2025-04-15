package com.klu.musicplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val selectedSong = intent.getParcelableExtra<Song>("selectedSong")

        if (savedInstanceState == null) {
            val fragment = PlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("selectedSong", selectedSong)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}
