package com.mediclab.smartreports.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.OriginalReport

class ReportsByPatientAdapter(
    private val originalReportList: List<OriginalReport>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ReportsByPatientAdapter.ReportsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reports, parent, false)
        return ReportsHolder(view)
    }

    override fun getItemCount() = originalReportList.size


    override fun onBindViewHolder(holder: ReportsHolder, position: Int) {

        val item = originalReportList[position]
        holder.bind(item)

    }

    inner class ReportsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView.findViewById<TextView>(R.id.datefech)
        val medic = itemView.findViewById<TextView>(R.id.medicreport)
        val btndetail = itemView.findViewById<TextView>(R.id.btndetail)

        fun bind(item: OriginalReport) {
            date.text = item.fecha
            medic.text = "${item.namedoc} ${item.lastnamedoc}"
            btndetail.setOnClickListener {
                listener.onItemClick(item.id)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

}