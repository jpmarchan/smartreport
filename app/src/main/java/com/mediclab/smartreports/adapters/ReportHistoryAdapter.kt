package com.mediclab.smartreports.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.OriginalReports


class ReportHistoryAdapter(
    private val reportHistory: List<OriginalReports>
) : RecyclerView.Adapter<ReportHistoryAdapter.HistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryHolder(view)
    }

    override fun getItemCount(): Int {
        return reportHistory.size
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        if (!reportHistory[position].status) {
            holder.tvId.text = "000"+reportHistory[position].id
            holder.tvDate.text = reportHistory[position].fecha
        }

    }

    class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId = itemView.findViewById<TextView>(R.id.tvReportId)
        val tvDate = itemView.findViewById<TextView>(R.id.tvReportDate)
    }
}