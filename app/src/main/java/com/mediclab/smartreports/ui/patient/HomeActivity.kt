package com.mediclab.smartreports.ui.patient

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.adapters.ReportHistory
import com.mediclab.smartreports.adapters.ReportHistoryAdapter
import com.mediclab.smartreports.adapters.ReportsByPatientAdapter
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
    lateinit var rvReports: RecyclerView
    lateinit var rvHistory: RecyclerView
    lateinit var idUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        rvReports = findViewById(R.id.rvReports)
        rvHistory = findViewById(R.id.rvHistory)
        setName = findViewById(R.id.setName)
        setName.text = Memory.userName
        idUser = Memory.id

        loadReportsHistory()
        loadReportsFromService()
    }

    private fun loadReportsFromService() {
        Api.retrofitService.getReportByPatient(Memory.token, idUser)
            .enqueue(object : Callback<List<OriginalReports>> {
                override fun onResponse(
                    call: Call<List<OriginalReports>>,
                    response: Response<List<OriginalReports>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val adapter = ReportsByPatientAdapter(response.body()!!)
                        rvReports.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<OriginalReports>>, t: Throwable) {
                    Logger.d("onFailure: ${t.message}")
                }
            })
    }

    private fun loadReportsHistory() {
        val fakeList = listOf(
            ReportHistory("00000114", "01/06/2020 10:05:16 AM"),
            ReportHistory("00000113", "30/05/2020 15:22:08 AM"),
            ReportHistory("00000112", "29/05/2020 20:09:23 AM"),
            ReportHistory("00000111", "27/05/2020 09:35:46 AM"),
            ReportHistory("00000110", "25/05/2020 13:47:53 AM")
        )
        val historyAdapter = ReportHistoryAdapter(fakeList)
        rvHistory.adapter = historyAdapter
    }
}