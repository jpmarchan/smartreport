package com.mediclab.smartreports.ui.patient

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.adapters.ReportHistoryAdapter
import com.mediclab.smartreports.adapters.ReportsByPatientAdapter
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.OriginalReport
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
    lateinit var tvEmpty: TextView
    lateinit var originalReportList: List<OriginalReport>

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
        tvEmpty = findViewById(R.id.tvEmpty)
    }

    private fun loadReportsFromService(listener: ReportsByPatientAdapter.OnItemClickListener) {
        Api.retrofitService.getReportByPatient(Memory.token, idUser)
            .enqueue(object : Callback<List<OriginalReport>> {
                override fun onResponse(
                    call: Call<List<OriginalReport>>,
                    response: Response<List<OriginalReport>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        originalReportList = response.body()!!

                        if (originalReportList.isNotEmpty()) {
                            val listTrue = originalReportList.filter { report -> report.status }
                            val adapter = ReportsByPatientAdapter(listTrue, listener)
                            rvReports.adapter = adapter

                            val listFalse= originalReportList.filter { report -> !report.status }
                            val adapterHistory = ReportHistoryAdapter(listFalse)
                            rvHistory.adapter = adapterHistory
                        }else{
                            tvEmpty.visibility = View.VISIBLE
                            rvReports.visibility = View.INVISIBLE

                        }

                    }
                }

                override fun onFailure(call: Call<List<OriginalReport>>, t: Throwable) {
                    Logger.d("onFailure: ${t.message}")
                }
            })
    }

    override fun onItemClick(id: Int) {
        goTo(DetailReportActivity::class.java,false,"id_report","$id")
    }
}