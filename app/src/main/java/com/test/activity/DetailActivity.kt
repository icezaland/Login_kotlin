package com.test.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import com.test.myapplication.Applications
import com.test.myapplication.R
import com.test.network.HandleVolleyError
import com.test.network.WebService
import com.test.util.Constants
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    private var context: Context? = null
    private var cusId: String? = null
    private var txvCusId: TextView? = null
    private var txvCusName: TextView? = null
    private var txvCusSex: TextView? = null
    private var txvCusGrade: TextView? = null
    private var txvCusPremium: TextView? = null
    private val TAG = "DetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        context = this
        cusId = intent.extras?.getString("id")
        initView()
        checkToken(cusId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        val toolbar: Toolbar? = findViewById(R.id.toolBar)
        toolbar?.title = "Customer Detail"
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        txvCusId = findViewById(R.id.item_detail_txv_cus_id)
        txvCusName = findViewById(R.id.item_detail_txv_cus_name)
        txvCusSex = findViewById(R.id.item_detail_txv_cus_sex)
        txvCusGrade = findViewById(R.id.item_detail_txv_cus_grade)
        txvCusPremium = findViewById(R.id.item_detail_txv_cus_is_premium)
    }

    private fun checkToken(cusId: String?) {
        val server = WebService()
        server.getCustomerDetail(
            cusId,
            Applications.getDb().getAppValue(Constants.Database.key.TOKEN),
            resOk,
            resErr
        )
    }

    private val resOk = Response.Listener<JSONObject> {
        txvCusId?.text = it.getString("id")
        txvCusName?.text = it.getString("name")
        txvCusSex?.text = it.getString("sex")
        txvCusGrade?.text = it.getString("customerGrade")
        val textPremium: String = if (it.getBoolean("isCustomerPremium")) {
            "Premium"
        } else {
            "Not Premium"
        }
        txvCusPremium?.text = textPremium
    }

    private val resErr = Response.ErrorListener {
        Log.e(TAG, "resErr: " + HandleVolleyError.handleVolleyError(it))
        Toast.makeText(context, "กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show()
    }
}