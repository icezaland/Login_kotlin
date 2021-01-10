package com.test.network

import androidx.annotation.Keep
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.test.myapplication.Applications

class VolleySingleton {

    init {
        mRequestQueue = Volley.newRequestQueue(Applications.getAppContext())
    }

    companion object {
        private var sInstance: VolleySingleton? = null
        private var mRequestQueue: RequestQueue? = null

        fun getInstance(): VolleySingleton? {
            if (sInstance == null) {
                sInstance = VolleySingleton()
            }
            return sInstance
        }

    }

    fun getRequestQueue(): RequestQueue? {
        return mRequestQueue
    }
}