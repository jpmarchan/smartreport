package com.mediclab.smartreports.ui.patient

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
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
    lateinit var idUser: String
    lateinit var tvEmpty1: TextView
    lateinit var lp: TextView
    lateinit var tvEmpty2: TextView

    lateinit var imgListReports: ImageView
    lateinit var datefechH: TextView
    lateinit var btndetail: Button
    lateinit var medich: TextView
    lateinit var nroreport: TextView
    lateinit var titledate: TextView
    lateinit var titlename: TextView
    lateinit var idreport: TextView




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
        setName = findViewById(R.id.setName)
        tvEmpty1 = findViewById(R.id.tvEmpty1)
        tvEmpty2 = findViewById(R.id.tvEmpty2)
        lp = findViewById(R.id.lp)
        datefechH = findViewById(R.id.datefechh)
        medich = findViewById(R.id.medicreporth)
        btndetail = findViewById(R.id.btndetailh)
        imgListReports = findViewById(R.id.btnList)
        titledate = findViewById(R.id.textView4h)
        titlename = findViewById(R.id.textView5h)
        nroreport = findViewById(R.id.textViewid)
        idreport = findViewById(R.id.idreport)

    }

    private fun loadReportsFromService() {


        Api.retrofitService.getReportByPatientOne(Memory.token, idUser)
            .enqueue(object : Callback<OriginalReport> {
                override fun onResponse(
                    call: Call<OriginalReport>,
                    response: Response<OriginalReport>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        reportValidateNoEmpty()
                        val data = response.body()!!
                        idreport.text = "000${data.id}"
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

    private fun reportValidateNoEmpty(){
        nroreport.visibility = View.VISIBLE
        idreport.visibility = View.VISIBLE
        datefechH.visibility = View.VISIBLE
        medich.visibility = View.VISIBLE
        btndetail.visibility = View.VISIBLE
        titledate.visibility = View.VISIBLE
        titlename.visibility = View.VISIBLE
        tvEmpty1.visibility = View.INVISIBLE
        tvEmpty2.visibility = View.INVISIBLE
        lp.visibility = View.INVISIBLE

    }
    private fun onClickEvents() {
        imgListReports.setOnClickListener {
            goTo(ReportsListActivity::class.java)
        }
    }

}