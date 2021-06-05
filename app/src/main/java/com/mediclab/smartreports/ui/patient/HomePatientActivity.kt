package com.mediclab.smartreports.ui.patient

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.adapters.ReportsByPatientAdapter
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.OriginalReports
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.utils.Logger
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePatientActivity : BaseActivity(), ReportsByPatientAdapter.OnItemClickListener {

    lateinit var setName: TextView
    lateinit var rvReports: RecyclerView
    lateinit var rvHistory: RecyclerView
    lateinit var idUser: String
    lateinit var originalReportList: List<OriginalReports>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bind()
        setName.text = Memory.userName
        idUser = Memory.id
        loadReportsFromService(this)

    }

    private fun bind() {
        rvReports = findViewById(R.id.rvReports)
        rvHistory = findViewById(R.id.rvHistory)
        setName = findViewById(R.id.setName)
    }

    private fun loadReportsFromService(listener: ReportsByPatientAdapter.OnItemClickListener) {
        Api.retrofitService.getReportByPatient(Memory.token, idUser)
            .enqueue(object : Callback<List<OriginalReports>> {
                override fun onResponse(
                    call: Call<List<OriginalReports>>,
                    response: Response<List<OriginalReports>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        originalReportList = response.body()!!

                        if (originalReportList.isNotEmpty()) {
                            val listTrue = originalReportList.filter { report -> report.status }
                            val adapter = ReportsByPatientAdapter(listTrue, listener)
                            rvReports.adapter = adapter
                        }


                        /* val adapterHistory = ReportHistoryAdapter(response.body()!!)*/
                        /*rvHistory.adapter = adapterHistory*/
                    }
                }

                override fun onFailure(call: Call<List<OriginalReports>>, t: Throwable) {
                    Logger.d("onFailure: ${t.message}")
                }
            })
    }

    override fun onItemClick(id: Int) {
        Toast.makeText(this, "ID : $id", Toast.LENGTH_SHORT).show()
    }
}