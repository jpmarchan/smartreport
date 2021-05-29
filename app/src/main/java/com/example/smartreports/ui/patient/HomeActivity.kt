package com.example.smartreports.ui.patient

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartreports.R
import com.example.smartreports.adapters.HistoryPatientAdapter
import com.example.smartreports.data.sign.Api
import com.example.smartreports.data.sign.OriginalReports
import com.example.smartreports.ui.BaseActivity
import com.example.smartreports.utils.Logger
import com.example.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : BaseActivity() {

    lateinit var setName: TextView
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recycler = findViewById(R.id.recyclerView)
        setName = findViewById(R.id.setName)
        setName.text = Memory.userName

        loadDataFromService()
    }

    private fun loadDataFromService(){

        Api.retrofitService.getReportByPatient(Memory.token, "2").enqueue(object : Callback<List<OriginalReports>> {
            override fun onResponse(
                call: Call<List<OriginalReports>>,
                response: Response<List<OriginalReports>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val adapter = HistoryPatientAdapter(response.body()!!, this@HomeActivity)
                    recycler.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<OriginalReports>>, t: Throwable) {
                Logger.d("onFailure: ${t.message}")
            }

        })
    }
}