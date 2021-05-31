package com.example.smartreports.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.time.*
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.smartreports.R
import com.example.smartreports.data.sign.OriginalReports
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger

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