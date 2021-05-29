package com.example.smartreports.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartreports.R
import com.example.smartreports.data.sign.OriginalReports

class HistoryPatientAdapter(
    private val originalReportList: List<OriginalReports>,
    var context: Context
    ) : RecyclerView.Adapter<HistoryPatientAdapter.HistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reports, parent,false)
        return HistoryHolder(view)
    }

    class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun getItemCount(): Int {
        return originalReportList.size
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {

    }
}