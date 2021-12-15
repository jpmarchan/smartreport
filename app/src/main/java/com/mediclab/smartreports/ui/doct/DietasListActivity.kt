package com.mediclab.smartreports.ui.doct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.adapters.DietasPatientAdapter
import com.mediclab.smartreports.adapters.ReportHistoryAdapter
import com.mediclab.smartreports.adapters.ReportsByPatientAdapter
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.DietaPatient
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.ui.patient.DetailReportActivity
import com.mediclab.smartreports.utils.Logger
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DietasListActivity : BaseActivity(), DietasPatientAdapter.OnItemClickListener {

    lateinit var originalReportList: List<DietaPatient>
    lateinit var rvDietas: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dietas_list)
        bind()
        loadDietasFromService(this)
    }

    private fun bind() {
        rvDietas = findViewById(R.id.rvdietas)

    }
    private fun loadDietasFromService(listener: DietasPatientAdapter.OnItemClickListener) {
        Api.retrofitService.getDietaPatient(Memory.token)
            .enqueue(object : Callback<List<DietaPatient>> {
                override fun onResponse(
                    call: Call<List<DietaPatient>>,
                    response: Response<List<DietaPatient>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        originalReportList = response.body()!!

                        if (originalReportList.isNotEmpty()) {
                            val listTrue = originalReportList
                            if(listTrue.isNotEmpty()){
                                val adapter = DietasPatientAdapter(listTrue, listener)
                                rvDietas.adapter = adapter

                            }

                        }

                    }
                }

                override fun onFailure(call: Call<List<DietaPatient>>, t: Throwable) {
                    Logger.d("onFailure: ${t.message}")
                }
            })
    }

    override fun onItemClick(id: Int) {
        val idreport = getIntent().getExtras()?.get("idreport")
        goTo1(DetailDietaActivity::class.java,false,"id_dieta","$id","idreport","$idreport")
    }
}