package com.mediclab.smartreports.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.OriginalReports

class ReportsByPatientAdapter(
    private val originalReportList: List<OriginalReports>
) : RecyclerView.Adapter<ReportsByPatientAdapter.ReportsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reports, parent, false)
        return ReportsHolder(view)
    }

    override fun getItemCount(): Int {
        return originalReportList.size
    }

    override fun onBindViewHolder(holder: ReportsHolder, position: Int) {
        if (originalReportList[position].status) {
            holder.date.text = originalReportList[position].fecha
            holder.medic.text = originalReportList[position].namedoc
        }
    }

    class ReportsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView.findViewById<TextView>(R.id.datefech)
        val medic = itemView.findViewById<TextView>(R.id.medicreport)
    }
}