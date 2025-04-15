package com.klu.musicplayer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class LogoutActivity : AppCompatActivity() {

    private lateinit var btnLogoutConfirm: Button
    private lateinit var btnLogoutCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)

        btnLogoutConfirm = findViewById(R.id.btn_logout_confirm)
        btnLogoutCancel = findViewById(R.id.btn_logout_cancel)

        btnLogoutConfirm.setOnClickListener {
            // Clear login/session state here if you store it
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnLogoutCancel.setOnClickListener {
            finish() // Go back to previous screen
        }
    }
}
