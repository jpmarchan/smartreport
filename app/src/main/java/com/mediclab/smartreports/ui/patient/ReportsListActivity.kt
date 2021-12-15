package com.mediclab.smartreports.ui.patient

import android.os.Bundle
import android.view.View
import android.widget.ImageView
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

class ReportsListActivity : BaseActivity(), ReportsByPatientAdapter.OnItemClickListener {

    lateinit var setName: TextView
    lateinit var rvReports: RecyclerView
    lateinit var idUser: String
    lateinit var lpl: TextView
    lateinit var text1: TextView
    lateinit var text2: TextView
    lateinit var lplh: TextView
    lateinit var text3: TextView
    lateinit var text4: TextView
    lateinit var originalReportList: List<OriginalReport>
    lateinit var btnHome: ImageView
    lateinit var rvHistory: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports_list)

        bind()

        setName.text = Memory.userName
        idUser = Memory.id
        loadReportsFromService(this)
        onClickEvents()
    }


    private fun bind() {
        rvReports = findViewById(R.id.rvReports)
        setName = findViewById(R.id.setName)
        lpl = findViewById(R.id.lpl)
        text1 = findViewById(R.id.tvEmpty1l)
        text2 = findViewById(R.id.tvEmptyl)
        lplh = findViewById(R.id.lplh)
        text3 = findViewById(R.id.tvEmpty1lh)
        text4 = findViewById(R.id.tvEmptylh)
        btnHome = findViewById(R.id.btnHome)
        rvHistory = findViewById(R.id.rvHistory)

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
                            if(listTrue.isNotEmpty()){
                                val adapter = ReportsByPatientAdapter(listTrue, listener)
                                rvReports.adapter = adapter

                            }else{
                                visibleReports()
                            }


                            val listFalse= originalReportList.filter { report -> !report.status }
                            if(listFalse.isNotEmpty()){
                                val adapterHistory = ReportHistoryAdapter(listFalse)
                                rvHistory.adapter = adapterHistory
                            }else{
                                visibleHistoryReports()
                            }


                        }else{
                            visibleReports()
                            visibleHistoryReports()
                        }

                    }
                }

                override fun onFailure(call: Call<List<OriginalReport>>, t: Throwable) {
                    Logger.d("onFailure: ${t.message}")
                }
            })
    }
    private fun onClickEvents() {
        btnHome.setOnClickListener {
            goTo(HomePatientActivity::class.java,true)
        }
    }
    private fun visibleReports(){
        lpl.visibility = View.VISIBLE
        text1.visibility = View.VISIBLE
        text2.visibility = View.VISIBLE
        rvReports.visibility = View.INVISIBLE
    }
    private fun visibleHistoryReports(){
        lplh.visibility = View.VISIBLE
        text3.visibility = View.VISIBLE
        text4.visibility = View.VISIBLE
        rvHistory.visibility = View.INVISIBLE
    }


    override fun onItemClick(id: Int) {
        goTo(DetailReportActivity::class.java,false,"id_report","$id")
    }
}