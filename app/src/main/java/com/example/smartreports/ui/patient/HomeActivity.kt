package com.example.smartreports.ui.patient

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartreports.R
import com.example.smartreports.adapters.HistorPatientAdapter
import com.example.smartreports.ui.BaseActivity
import com.example.smartreports.utils.Memory

data class Reports(val name: String = "", val image: String = "", val description: String = "")

class HomeActivity : BaseActivity() {
    lateinit var setName: TextView
    private var dataReports= ArrayList<Reports>()
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recycler = findViewById(R.id.recyclerView)
        setName = findViewById(R.id.setName)
        setName.text = Memory.userName


        loadFakeData()
        setupRecycler()
    }

    private fun loadFakeData(){
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
    }

    private fun setupRecycler(){
        val adapter = HistorPatientAdapter(dataReports, this)
        recycler.adapter = adapter
    }
}