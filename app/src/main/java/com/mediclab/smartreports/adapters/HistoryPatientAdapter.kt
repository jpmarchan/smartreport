package com.mediclab.smartreports.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.OriginalReports

class HistoryPatientAdapter(
    private val originalReportList: List<OriginalReports>,
    var context: Context
    ) : RecyclerView.Adapter<HistoryPatientAdapter.HistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reports, parent,false)
        return HistoryHolder(view)
    }



    override fun getItemCount(): Int {
        return originalReportList.size
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
            if(originalReportList[position].status){
                holder.date.text = originalReportList[position].fecha
                holder.medic.text = originalReportList[position].namedoc
            }

    }

    class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val date = itemView.findViewById<TextView>(R.id.datefech)
        val medic = itemView.findViewById<TextView>(R.id.medicreport)

    }
}