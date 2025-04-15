package com.klu.musicplayer

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout

class SettingsActivity : AppCompatActivity() {

    private lateinit var switchNotifications: Switch
    private lateinit var switchDarkMode: Switch
    private lateinit var layoutAbout: LinearLayout

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        switchNotifications = findViewById(R.id.switch_notifications)
        switchDarkMode = findViewById(R.id.switch_dark_mode)
        layoutAbout = findViewById(R.id.layout_about)

        // SharedPreferences to store toggle state
        sharedPrefs = getSharedPreferences("AppSettings", MODE_PRIVATE)

        // Load saved states
        switchNotifications.isChecked = sharedPrefs.getBoolean("notifications_enabled", true)
        switchDarkMode.isChecked = sharedPrefs.getBoolean("dark_mode_enabled", false)

        // Save toggle states on change
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("notifications_enabled", isChecked).apply()
            Toast.makeText(this, if (isChecked) "Notifications On" else "Notifications Off", Toast.LENGTH_SHORT).show()
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("dark_mode_enabled", isChecked).apply()
            Toast.makeText(this, if (isChecked) "Dark Mode On" else "Dark Mode Off", Toast.LENGTH_SHORT).show()
            // Optional: You can add theme switching here if needed
        }

        layoutAbout.setOnClickListener {
            Toast.makeText(this, "Music Player v1.0", Toast.LENGTH_LONG).show()
        }
    }
}
