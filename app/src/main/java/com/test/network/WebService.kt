package com.test.network

import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class WebService() {

    private var volleySingleton: VolleySingleton? = null
    private var requestQueue: RequestQueue? = null
    private val socketTimeout: Int = 30000
    private val path = "https://us-central1-iostesting-b3165.cloudfunctions.net/mobileApi/api/v1/"

    init {
        volleySingleton = VolleySingleton.getInstance()
        requestQueue = volleySingleton?.getRequestQueue()
    }

    fun login(
        username: String,
        password: String,
        resOk: Response.Listener<JSONObject>,
        resErr: Response.ErrorListener
    ) {
        val jsonBody = JSONObject()
        jsonBody.put("username", "test")
        jsonBody.put("password", "123456")
        val url = path + "login"
        val request =
            JsonObjectRequest(Request.Method.POST, url, jsonBody.toString(), resOk, resErr)
        val policy: RetryPolicy = DefaultRetryPolicy(
            socketTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        request.retryPolicy = policy
        requestQueue?.add(request)
    }

    fun getCustomerDetail(
        customerId: String?,
        token: String?,
        resOk: Response.Listener<JSONObject>,
        resErr: Response.ErrorListener
    ) {
        val jsonBody = JSONObject()
        jsonBody.put("customerId", customerId)
        jsonBody.put("token", token)
        val url = path + "getCustomerDetail"
        val request =
            JsonObjectRequest(Request.Method.POST, url, jsonBody.toString(), resOk, resErr)
        val policy: RetryPolicy = DefaultRetryPolicy(
            socketTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        request.retryPolicy = policy
        requestQueue?.add(request)
    }
}