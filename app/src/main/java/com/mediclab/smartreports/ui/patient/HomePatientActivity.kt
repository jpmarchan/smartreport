package com.mediclab.smartreports.ui.patient

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.adapters.ReportHistoryAdapter
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.OriginalReport
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.utils.Logger
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomePatientActivity : BaseActivity() {

    lateinit var setName: TextView
    lateinit var rvHistory: RecyclerView
    lateinit var idUser: String
    lateinit var tvEmpty: TextView
    lateinit var originalReportList: List<OriginalReport>
    lateinit var imgListReports: ImageView
    lateinit var datefechH: TextView
    lateinit var btndetail: Button
    lateinit var medich: TextView
    lateinit var titledate: TextView
    lateinit var titlename: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bind()

        setName.text = Memory.userName
        idUser = Memory.id
        loadReportsFromService()
        onClickEvents()

    }

    private fun bind() {
        rvHistory = findViewById(R.id.rvHistory)
        setName = findViewById(R.id.setName)
        tvEmpty = findViewById(R.id.tvEmpty)
        datefechH = findViewById(R.id.datefechh)
        medich = findViewById(R.id.medicreporth)
        btndetail = findViewById(R.id.btndetailh)
        imgListReports = findViewById(R.id.btnList)
        titledate = findViewById(R.id.textView4h)
        titlename = findViewById(R.id.textView5h)

    }

    private fun loadReportsFromService() {
        Api.retrofitService.getReportByPatient(Memory.token, idUser)
            .enqueue(object : Callback<List<OriginalReport>> {
                override fun onResponse(
                    call: Call<List<OriginalReport>>,
                    response: Response<List<OriginalReport>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        originalReportList = response.body()!!

                        if (originalReportList.isNotEmpty()) {

                            val listFalse= originalReportList.filter { report -> !report.status }
                            val adapterHistory = ReportHistoryAdapter(listFalse)
                            rvHistory.adapter = adapterHistory
                        }else{
                            tvEmpty.visibility = View.VISIBLE
                            datefechH.visibility = View.INVISIBLE
                            medich.visibility = View.INVISIBLE
                            btndetail.visibility = View.INVISIBLE
                            titledate.visibility = View.INVISIBLE
                            titlename.visibility = View.INVISIBLE
                        }
                    }
                }
                override fun onFailure(call: Call<List<OriginalReport>>, t: Throwable) {
                    Logger.d("onFailure: ${t.message}")
                }
            })

        Api.retrofitService.getReportByPatientOne(Memory.token, idUser)
            .enqueue(object : Callback<OriginalReport> {
                override fun onResponse(
                    call: Call<OriginalReport>,
                    response: Response<OriginalReport>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val data = response.body()!!
                        datefechH.text = "${data.fecha}"
                        medich.text = "${data.namedoc} ${data.lastnamedoc}"
                        val id = data.id
                        btndetail.setOnClickListener {
                            goTo(DetailReportActivity::class.java,false,"id_report","$id")
                        }
                    }
                }
                override fun onFailure(call: Call<OriginalReport>, t: Throwable) {
                    Logger.d("onFailure: ${t.message}")
                }
            })



    }
    private fun onClickEvents() {
        imgListReports.setOnClickListener {
            goTo(ReportsListActivity::class.java)
        }
    }

}