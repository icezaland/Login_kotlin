package com.test.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.adapter.DashBoardAdapter
import com.test.myapplication.Applications
import com.test.myapplication.R
import com.test.util.Constants

class DashBoardActivity : AppCompatActivity(), DashBoardAdapter.ClickListener {

    private var context: Context? = null
    private val TAG = "DashBoardActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        context = this
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                Applications.getDb().deleteAppValue(Constants.Database.key.TOKEN)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        val toolbar: Toolbar = findViewById(R.id.toolBar)
        toolbar.title = "DashBroad"
        setSupportActionBar(toolbar)
        setupRcv()
    }

    private fun setupRcv() {
        val mAdapter = DashBoardAdapter(context, Applications.getDb().getCustomerList(),this)
        val mRecyclerView: RecyclerView? = findViewById(R.id.login_rcv)
        mRecyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRecyclerView?.itemAnimator = DefaultItemAnimator()
        mRecyclerView?.setHasFixedSize(false)
        mRecyclerView?.adapter = mAdapter

    }

    override fun onClickItem(view: View?) {
        val txvId: TextView? = view?.findViewById(R.id.item_dashboard_txv_cus_id)
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("id", txvId?.text.toString())
        startActivity(intent)
    }
}