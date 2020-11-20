package com.e.project_faces_recogniton

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.e.project_faces_recogniton.model.Event

class MainDetails : AppCompatActivity() {
    private lateinit var timeStamp: TextView
    private lateinit var location: TextView
    private lateinit var camera_name: TextView
    private lateinit var lastName: TextView
    private lateinit var firstName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_layout)
        timeStamp = findViewById(R.id.timeStamp)
        location = findViewById(R.id.location)
        camera_name = findViewById(R.id.camera_name)
        lastName = findViewById(R.id.lastName)
        firstName = findViewById(R.id.firstName)
        val intent = intent
        val ev =
            intent.getSerializableExtra("myJson") as Event?
        timeStamp.setText(ev!!.timestamp)
        location.setText(ev.camera.location)
        camera_name.setText(ev.camera.camera_name)
        lastName.setText(ev.people.last_name)
        firstName.setText(ev.people.first_name)
        findViewById<View>(R.id.deconnexion).setOnClickListener{
            val launchactivity = Intent(this@MainDetails, MainNotification::class.java)
            startActivity(launchactivity)
        }
        findViewById<View>(R.id.retour).setOnClickListener{
            val launchactivity = Intent(this@MainDetails, MainNotification::class.java)
            startActivity(launchactivity)
        }
    }
}