package com.e.project_faces_recogniton.volley

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.e.project_faces_recogniton.MainActivity
import com.e.project_faces_recogniton.model.User

class SharedPrefManager {
    constructor(context: Context?){
        mCtx = context
    }

    fun userLogin(user: User){
        var sharedPreferences:SharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(KEY_ID, user.id);
        editor.putString(KEY_NOM, user.nom);
        editor.putString(KEY_PRENOM, user.prenom);
        editor.putString(KEY_EMAIL, user.email);
        editor.putString(KEY_PASSWORD, user.password);
        editor.apply();
    }

    fun isLoggedIn(): Boolean{
        var sharedPreferences:SharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_NOM, null) != null
    }

    fun getUser(): User{
        var sharedPreferences:SharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return User(
            sharedPreferences.getInt(KEY_ID, -1),
            sharedPreferences.getString(KEY_NOM, null),
            sharedPreferences.getString(KEY_PRENOM, null),
            sharedPreferences.getString(KEY_EMAIL, null),
            sharedPreferences.getString(KEY_PASSWORD, null)
        )
    }

    fun logout(){
        var sharedPreferences:SharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        val intent = Intent(mCtx, MainActivity::class.java)
        mCtx!!.startActivity(intent)
    }

    companion object {
        //the constants
        private const val SHARED_PREF_NAME = "simplifiedcodingsharedpref"
        private const val KEY_NOM = "keynom"
        private const val KEY_PRENOM = "keyprenom"
        private const val KEY_EMAIL = "keyemail"
        private const val KEY_PASSWORD = "keypassword"
        private const val KEY_ID = "keyid"
        private var mInstance: SharedPrefManager? = null
        private var mCtx: Context? = null

        @Synchronized
        fun getInstance(context: Context?): SharedPrefManager? {
            if (SharedPrefManager.mInstance == null) {
                SharedPrefManager.mInstance = SharedPrefManager(context)
            }
            return SharedPrefManager.mInstance
        }
    }
}