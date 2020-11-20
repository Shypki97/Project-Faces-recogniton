package com.e.project_faces_recogniton

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.e.project_faces_recogniton.model.User
import com.e.project_faces_recogniton.volley.SharedPrefManager
import com.e.project_faces_recogniton.volley.URLs
import com.e.project_faces_recogniton.volley.VolleySingleton
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText

        findViewById<View>(R.id.buttonLogin).setOnClickListener { userLogin() }
    }

    private fun userLogin() {
        val email = editTextEmail!!.text.toString()
        val password = editTextPassword!!.text.toString()

        if (TextUtils.isEmpty(email)) {
            editTextEmail!!.error = "Merci d'entrer votre email"
            editTextEmail!!.requestFocus()
            return
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword!!.error = "Merci d'entrer votre mot de passe"
            editTextPassword!!.requestFocus()
            return
        }
        jsonArray(URLs.ROOT_URL, email, password)
    }

    fun jsonArray(url: String?, email: String, password: String) {
        val jsonArrayRequest: JsonArrayRequest =
            object : JsonArrayRequest(
                Method.GET, url, null,
                Response.Listener { response ->
                    progressBar!!.visibility = View.GONE
                    val arrayUser: ArrayList<User> = ArrayList<User>()
                    //parcours de l'api en JSON puis remplissage du tableaux
                    for (i in 0 until response.length()) {
                        try {
                            val obj: JSONObject = response.getJSONObject(i)
                            val user: User = connexionRemplir(obj)
                            arrayUser.add(user)
                            SharedPrefManager.getInstance(applicationContext)
                                ?.userLogin(user)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    condition(arrayUser, email, password)
                },
                Response.ErrorListener { error ->
                    Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                        .show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["email"] = email
                    params["password"] = password
                    return params
                }
            }
        VolleySingleton.getInstance(this)?.addToRequestQueue(jsonArrayRequest)
    }

    @Throws(JSONException::class)
    fun connexionRemplir(obj: JSONObject): User {
        return User(
            obj.getInt("id"),
            obj.getString("nom"),
            obj.getString("prenom"),
            obj.getString("email"),
            obj.getString("password")
        )
    }

    fun condition(
        arrayUser: ArrayList<User>,
        email: String?,
        password: String?
    ) {

        for (p in 0 until arrayUser.size) {
            if (arrayUser[p].email.equals(email) && arrayUser[p].password
                    .equals(password)
            ) {
                //Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                val launchactivity = Intent(this@MainActivity, MainNotification::class.java)
                startActivity(launchactivity)
            } else if (!arrayUser[p].email.equals(email) && arrayUser.size == p + 1) {
                editTextEmail!!.error = "email incorrect"
                editTextEmail!!.requestFocus()
            } else if (!arrayUser[p].email
                    .equals(password) && arrayUser.size == p + 1
            ) {
                editTextPassword!!.error = "mot de passe incorrect"
                editTextPassword!!.requestFocus()
            }
        }
    }
}