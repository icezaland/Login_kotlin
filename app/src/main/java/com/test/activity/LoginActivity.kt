package com.test.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.test.myapplication.Applications
import com.test.myapplication.R
import com.test.network.HandleVolleyError
import com.test.network.WebService
import com.test.util.Constants
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private var context: Context? = null
    private var pgBar: ProgressBar? = null

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this
        initView()
        checkLogin()
    }

    private fun initView() {
        val edtUsermame: EditText = findViewById(R.id.login_edit_username)
        val edtPassword: EditText = findViewById(R.id.login_edit_password)
        val btnSubmit: Button = findViewById(R.id.login_btn_summit);
        pgBar = findViewById(R.id.login_pg)

        btnSubmit?.setOnClickListener(View.OnClickListener {
            getLogin(edtUsermame.text.toString(), edtPassword.text.toString())
            pgBar?.visibility = View.VISIBLE
        })
    }

    private fun getLogin(username: String, password: String) {
        val server = WebService()
        server.login(username, password, resOk, resErr)
        pgBar?.visibility = View.VISIBLE
    }

    private fun checkLogin() {
        if (Applications.getDb().getAppValue(Constants.Database.key.TOKEN) != null) {
            startActivity(Intent(context, DashBoardActivity::class.java))
        }
    }

    private val resOk = Response.Listener<JSONObject> {
        pgBar?.visibility = View.GONE
        Log.d(TAG, "resOk : $it")
        try {
            if (it.getInt("status") == 200) {
                val token = it.getString("token")
                Applications.getDb().insertAppValue(Constants.Database.key.TOKEN, token)
                val jCustomerArr = it.getJSONArray("customers")
                for (i in 0 until jCustomerArr.length()) {
                    val jCustomer = jCustomerArr.getJSONObject(i)
                    Applications.getDb().insertCustomerInformation(
                        jCustomer.getString(Constants.WebService.CUSTOMER_ID),
                        jCustomer.getString(Constants.WebService.CUSTOMER_NAME)
                    )
                }
                startActivity(Intent(context, DashBoardActivity::class.java))
            } else {
                Toast.makeText(context, "กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e(TAG, "resOk : " + e.message)
        }
    }

    private val resErr = Response.ErrorListener {
        Log.e(TAG, "resErr: " + HandleVolleyError.handleVolleyError(it))
        pgBar?.visibility = View.GONE
        Toast.makeText(context, "กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show()
    }


}