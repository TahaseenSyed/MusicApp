package com.klu.musicplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.klu.musicplayer.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 Dummy Data for Notifications
        val notifications = listOf(
            NotificationModel("New song added to your playlist!", "2 min ago"),
            NotificationModel("Your favorite artist released a new album!", "10 min ago"),
            NotificationModel("Weekly music recommendations are ready!", "1 hour ago"),
            NotificationModel("Update your app for the latest features!", "3 hours ago")
        )

        // Setup RecyclerView
        binding.recyclerViewNotifications.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = NotificationAdapter(notifications)
        }
    }
}
