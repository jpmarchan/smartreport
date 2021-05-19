package com.example.smartreports.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartreports.R
import com.example.smartreports.ui.patient.Reports

class HistorPatientAdapter(val dataHistory: ArrayList<Reports>, var context: Context) : RecyclerView.Adapter<HistorPatientAdapter.HstoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HstoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reports, parent,false)
        return HstoryHolder(view)
    }


    class HstoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun getItemCount(): Int {
        return dataHistory.size
    }

    override fun onBindViewHolder(holder: HstoryHolder, position: Int) {
    }
}