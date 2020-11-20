package com.e.project_faces_recogniton.volley

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


class VolleySingleton {
    var mRequestQueue: RequestQueue
    var mCtx: Context

     constructor(context: Context){
        mCtx = context
        mRequestQueue = getRequestQueue()
    }

        fun getRequestQueue(): RequestQueue{
            if (mRequestQueue == null){
                mRequestQueue = Volley.newRequestQueue(mCtx.applicationContext)
            }
            return mRequestQueue
        }

    fun <T> addToRequestQueue(req: Request<T>?) {
        mRequestQueue!!.add(req)
    }

    companion object{
        var mInstance: VolleySingleton? = null

        @Synchronized
        fun getInstance(context: Context?): VolleySingleton? {
            if (mInstance == null) {
                mInstance = VolleySingleton(context!!)
            }
            return mInstance
        }

    }

}