package com.mediclab.smartreports.ui.doct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.ListPatientResponse
import com.mediclab.smartreports.data.sign.SearchPatientParams
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSearchPatientActivity : BaseActivity() {

    lateinit var namedoc : TextView
    lateinit var namep : TextView
    lateinit var dnid : TextView
    lateinit var sexd : TextView
    lateinit var aged : TextView
    lateinit var textView19d : TextView
    var statusCbOne: Boolean = false
    var statusCb2: Boolean = false
    private lateinit var cbpatientd: CheckBox
    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton
    private  var idpaient : Int = 0
    lateinit var docsimg : TextView
    lateinit var docsimgasing : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_search_patient)
        bind()
        namedoc.text = Memory.userName
        listPatiend()
    }

    private  fun bind(){
        namep = findViewById(R.id.namep)
        dnid = findViewById(R.id.dnid)
        sexd = findViewById(R.id.sexd)
        aged = findViewById(R.id.aged)
        textView19d = findViewById(R.id.textView19d)
        cbpatientd = findViewById(R.id.cbpatientd)
        radioButton1 = findViewById(R.id.radioButton1)
        radioButton2 = findViewById(R.id.radioButton2)
        namedoc = findViewById(R.id.setNameDoc)
        docsimg = findViewById(R.id.textViewDocs)
        docsimgasing = findViewById(R.id.textViewDoc)
    }

    fun listPatiend(){
        val idreport= getIntent().getExtras()?.get("dnish");
        Api.retrofitService.searchPatientsByDni(SearchPatientParams(idreport.toString())).enqueue(object :
            Callback<ListPatientResponse> {
            override fun onResponse(
                call: Call<ListPatientResponse>,
                response: Response<ListPatientResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        namep.text = "${it.name}  ${it.lastname}"
                        dnid.text = it.dni.toString()
                        if(it.sex){
                            sexd.text = "Hombre"
                        }else{
                            sexd.text = "Mujer"
                        }
                        aged.text = it.age.toString()
                        idpaient = it.id
                    }
                } else {
                    dismissDialog()
                }
            }
            override fun onFailure(call: Call<ListPatientResponse>, t: Throwable) {
                dismissDialog()
            }
        })
    }


    fun checkBoxClic(v: View) {
        val  cbshearch = v as CheckBox
        cbshearch.isChecked
        if(cbshearch.isChecked){
            cbpatientd.visibility = View.VISIBLE

            statusCbOne = true
        }else{
            cbpatientd.visibility = View.GONE

            statusCb2 = false

        }
    }
    fun checkBoxClic1(v: View) {
        val  cbshearch = v as CheckBox
        cbshearch.isChecked
        if(cbshearch.isChecked){
            statusCb2 = true
            radioButton1.visibility = View.VISIBLE
            radioButton2.visibility = View.VISIBLE
            textView19d.visibility = View.VISIBLE
            docsimg.visibility = View.GONE
            docsimgasing.visibility = View.VISIBLE

        }else{
            radioButton1.visibility = View.GONE
            radioButton2.visibility = View.GONE
            textView19d.visibility = View.GONE
            docsimgasing.visibility = View.GONE
            docsimg.visibility = View.VISIBLE

            statusCb2 = false
        }
    }
}