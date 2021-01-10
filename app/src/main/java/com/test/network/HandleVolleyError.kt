package com.test.network

import com.android.volley.NoConnectionError
import com.android.volley.TimeoutError
import com.android.volley.VolleyError

class HandleVolleyError {
    private val TAG = "HandleVolleyError"

    companion object {
        fun handleVolleyError(error: VolleyError): String {
            var errorMessage = ""
            errorMessage = if (error.networkResponse != null) {
                "ServerError(" + error.networkResponse.statusCode + ")"
            } else {
                when (error) {
                    is TimeoutError -> {
                        "TimeoutError"
                    }
                    is NoConnectionError -> {
                        "NetworkError"
                    }
                    else -> {
                        "UnknownError"
                    }
                }
            }
            return errorMessage
        }
    }
}