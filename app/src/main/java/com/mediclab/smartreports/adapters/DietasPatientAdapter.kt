package com.mediclab.smartreports.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.DietaPatient

class DietasPatientAdapter(
    private val originalReportList: List<DietaPatient>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<DietasPatientAdapter.ReportsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dietas, parent, false)
        return ReportsHolder(view)
    }

    override fun getItemCount() = originalReportList.size


    override fun onBindViewHolder(holder: ReportsHolder, position: Int) {

        val item = originalReportList[position]
        holder.bind(item)

    }

    inner class ReportsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView.findViewById<TextView>(R.id.namedieta)
        val medic = itemView.findViewById<TextView>(R.id.description1)
        val btndetail = itemView.findViewById<TextView>(R.id.btndetail1)

        fun bind(item: DietaPatient) {
            date.text = item.name
            medic.text = "${item.description}"
            btndetail.setOnClickListener {
                listener.onItemClick(item.id)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

}