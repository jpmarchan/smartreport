package com.mediclab.smartreports.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R

data class ReportHistory(
    val reportId: String,
    val reportDate: String
)

class ReportHistoryAdapter(
    private val reportHistory: List<ReportHistory>
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
        holder.tvId.text = reportHistory[position].reportId
        holder.tvDate.text = reportHistory[position].reportDate
    }

    class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId = itemView.findViewById<TextView>(R.id.tvReportId)
        val tvDate = itemView.findViewById<TextView>(R.id.tvReportDate)
    }
}