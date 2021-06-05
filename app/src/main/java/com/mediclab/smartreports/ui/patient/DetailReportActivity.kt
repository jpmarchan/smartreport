package com.mediclab.smartreports.ui.patient

import android.os.Bundle
import android.widget.TextView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.ReportsDetail
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.utils.Logger
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailReportActivity : BaseActivity() {
    lateinit var codeReport : TextView
    lateinit var date : TextView
    lateinit var medic : TextView
    lateinit var setName: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_report)
        getReportById()
        bind()
        setName.text = Memory.userName

    }
    fun bind(){
        codeReport = findViewById(R.id.codereport)
        date = findViewById(R.id.dateInsert)
        medic = findViewById(R.id.nameMedicDetail)
        setName = findViewById(R.id.setNameDetail)

    }

    private fun getReportById(){
        val idreport= getIntent().getExtras()?.get("id_report");
        Api.retrofitService.getReportById(Memory.token,idreport.toString())
            .enqueue(object : Callback<List<ReportsDetail>> {
                override fun onResponse(
                    call: Call<List<ReportsDetail>>,
                    response: Response<List<ReportsDetail>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val reportdetail = response.body()!!
                        codeReport.text = "00${reportdetail[0].id}"
                        date.text = reportdetail[0].fecha
                        medic.text = "${reportdetail[0].namedoc} ${reportdetail[0].lastnamedoc}"
                    }
                }

                override fun onFailure(call: Call<List<ReportsDetail>>, t: Throwable) {
                    Logger.d("onFailure: ${t.message}")
                }
            })
    }

}