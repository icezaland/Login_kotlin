package com.test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.model.CustomerModel
import com.test.myapplication.R

class DashBoardAdapter(
    context: Context?,
    mDataList: List<CustomerModel>,
    listener: ClickListener
) :
    RecyclerView.Adapter<DashBoardAdapter.DashBoardViewHolder?>(), View.OnClickListener {

    private var mContext: Context? = null
    private val mData: List<CustomerModel>
    private lateinit var mListener: ClickListener;

    init {
        this.mContext = context
        this.mData = mDataList
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardViewHolder {
        return DashBoardViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_list_dashboard, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DashBoardViewHolder, position: Int) {
        val item: CustomerModel = mData[position]
        holder.txvId?.text = item.id
        holder.txvName?.text = item.name
        holder.rootView?.setOnClickListener(this)
        holder.txvId?.setOnClickListener(this)
        holder.txvName?.setOnClickListener(this)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    interface ClickListener {
        fun onClickItem(view: View?)
    }

    class DashBoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rootView: LinearLayout? = null
        var txvId: TextView? = null
        var txvName: TextView? = null

        init {
            rootView = itemView.findViewById(R.id.item_dashboard_root_view)
            txvId = itemView.findViewById(R.id.item_dashboard_txv_cus_id)
            txvName = itemView.findViewById(R.id.item_dashboard_txv_cus_name)
        }
    }

    override fun onClick(p0: View?) {
        mListener.onClickItem(p0)
    }
}