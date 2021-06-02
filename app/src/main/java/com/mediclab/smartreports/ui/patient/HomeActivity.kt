package com.mediclab.smartreports.ui.patient

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.adapters.HistoryPatientAdapter
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.OriginalReports
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.utils.Logger
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : BaseActivity() {

    lateinit var setName: TextView
    lateinit var recycler: RecyclerView
    lateinit var idUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recycler = findViewById(R.id.recyclerView)
        setName = findViewById(R.id.setName)
        setName.text = Memory.userName
        idUser = Memory.id

        loadDataFromService()
    }

    private fun loadDataFromService(){

        Api.retrofitService.getReportByPatient(Memory.token, idUser).enqueue(object : Callback<List<OriginalReports>> {
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