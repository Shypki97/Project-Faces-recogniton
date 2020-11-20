package com.e.project_faces_recogniton

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.e.project_faces_recogniton.model.Camera
import com.e.project_faces_recogniton.model.Event
import com.e.project_faces_recogniton.model.People
import com.e.project_faces_recogniton.volley.URLs
import com.e.project_faces_recogniton.volley.VolleySingleton
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainNotification : AppCompatActivity() {
    private val events: ArrayList<Event> = ArrayList<Event>()
    private var context: Context? = null
    private var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_layout)
        context = this
        listView = findViewById(R.id.listView)
        findViewById<View>(R.id.button_deconnexion).setOnClickListener{
            val launchactivity = Intent(this@MainNotification, MainActivity::class.java)
            startActivity(launchactivity)
        }
        jsonArray(URLs.NOTIF_URL)
    }

    fun jsonArray(urLs: String?) {
        val jsonArrayRequest =
            JsonArrayRequest(
                Request.Method.GET, urLs, null,
                Response.Listener { response ->
                    for (p in 0 until response.length()) {
                        try {
                            val objEvent = response.getJSONObject(p)
                            val objCamera = objEvent.getJSONObject("camera")
                            val objPeople = objEvent.getJSONObject("people")
                            val event: Event = eventRemplir(objEvent, objCamera, objPeople)
                            events.add(event)
                            val listView =
                                findViewById<ListView>(R.id.listView)
                            listView.adapter = CustomListAdapter(context, events)
                            onItemsClick(listView)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }, Response.ErrorListener { error ->
                    Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                        .show()
                })
        VolleySingleton.getInstance(this)?.addToRequestQueue(jsonArrayRequest)
    }

    @Throws(JSONException::class)
    fun eventRemplir(objEvent: JSONObject, objCamera: JSONObject, objPeople: JSONObject): Event {
        return Event(
            objEvent.getInt("event_id"),
            objEvent.getString("timestamp"),
            Camera(
                objCamera.getInt("camera_id"),
                objCamera.getString("cam_name"),
                objCamera.getString("location"),
                objCamera.getString("rtsp_flux")
            ),
            People(
                objPeople.getInt("people_id"),
                objPeople.getString("first_name"),
                objPeople.getString("last_name")
            )
        )
    }

    fun onItemsClick(listView: ListView) {
        listView.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            val o = listView.getItemAtPosition(i)
            val event: Event = o as Event
            val launchactivity = Intent(this@MainNotification, MainDetails::class.java)
            launchactivity.putExtra("myJson", event)
            startActivity(launchactivity)
        }
    }
}