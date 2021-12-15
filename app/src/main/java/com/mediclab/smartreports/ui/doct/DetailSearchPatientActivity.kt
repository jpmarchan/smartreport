package com.mediclab.smartreports.ui.doct

import android.os.Bundle
import android.view.View
import android.widget.*
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.*
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

    lateinit var nameptitle : TextView
    lateinit var dnidtitle : TextView
    lateinit var sexdtitle : TextView
    lateinit var agedtitle: TextView
    lateinit var anemin: TextView

    lateinit var lpnd : TextView
    lateinit var tvEmpty1nd : TextView
    lateinit var tvEmpty2nd: TextView


    lateinit var textView19d : TextView
    var statusCbOne: Boolean = false
    var statusCb2: Boolean = false
    var statusRbReport: Boolean = false

    private lateinit var cbpatient: CheckBox
    private lateinit var cbpatientd: CheckBox
    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var btnsent: Button

    private  var idpaient : Int = 0
    private  var responsefkidmedic : Int = 0

    lateinit var docsimg : TextView
    lateinit var docsimgasing : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_search_patient)
        bind()
        namedoc.text = Memory.userName
        listPatiend()
        onClickEvents()
    }



    private  fun bind(){
        namep = findViewById(R.id.namep)
        dnid = findViewById(R.id.dnid)
        sexd = findViewById(R.id.sexd)
        aged = findViewById(R.id.aged)
        textView19d = findViewById(R.id.textView19d)
        cbpatient = findViewById(R.id.cbpatient)
        cbpatientd = findViewById(R.id.cbpatientd)
        radioButton1 = findViewById(R.id.radioButton1)
        radioButton2 = findViewById(R.id.radioButton2)
        namedoc = findViewById(R.id.setNameDoc)
        docsimg = findViewById(R.id.textViewDocs)
        docsimgasing = findViewById(R.id.textViewDoc)
        btnsent = findViewById(R.id.btnsendreport)
        anemin = findViewById(R.id.textHemoglobina)
        nameptitle = findViewById(R.id.textView4nd)
        dnidtitle = findViewById(R.id.textView5nd)
        sexdtitle = findViewById(R.id.textView13nd)
        agedtitle = findViewById(R.id.textView17nd)

        lpnd = findViewById(R.id.lpnd)
        tvEmpty1nd = findViewById(R.id.tvEmpty1nd)
        tvEmpty2nd = findViewById(R.id.tvEmpty2nd)
    }

    private fun onClickEvents() {
        val idmedic = Memory.id.toInt()

        btnsent.setOnClickListener {
            if(responsefkidmedic != Memory.id.toInt()){
                Api.retrofitService.asingPatientMedic(AsingPatientMedicParams(idpaient, idmedic)).enqueue(object :
                    Callback<AsingPatientMedicResponse> {
                    override fun onResponse(
                        call: Call<AsingPatientMedicResponse>,
                        response: Response<AsingPatientMedicResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                if(it.status){
                                        goTo(SpeechReportActivity::class.java,false,"idpatient","${idpaient}")
                                        showToast("Paciente asignado.")

                                }else{
                                    showToast("Error al asignar paciente")
                                }
                            }
                        } else {
                            showToast("Error al asignar paciente")
                        }
                    }
                    override fun onFailure(call: Call<AsingPatientMedicResponse>, t: Throwable) {
                    }
                })
            }else{
                    goTo(SpeechReportActivity::class.java,false,"idpatient","${idpaient}")

            }

        }
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
                        visivilityInfoPatient()
                        namep.text = "${it.name}  ${it.lastname}"
                        dnid.text = it.dni.toString()
                        if(it.sex){
                            sexd.text = "Hombre"
                        }else{
                            sexd.text = "Mujer"
                        }
                        aged.text = it.age.toString()
                        anemin.text = it.aneminum.toString()
                        idpaient = it.id
                        responsefkidmedic  = it.fkidmedic
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
            if(responsefkidmedic == Memory.id.toInt()){
                visivility()

            }else{
                cbpatientd.visibility = View.VISIBLE
                statusCbOne = true
            }

        }else{
            cbpatientd.visibility = View.GONE
            inVisivility()
            statusCb2 = false

        }
    }
    fun checkBoxClic1(v: View) {
        val  cbshearch = v as CheckBox
        cbshearch.isChecked
        if(cbshearch.isChecked){
            statusCb2 = true
            visivility()

        }else{
            inVisivility()
            statusCb2 = false
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.radioButton1 ->
                    if (checked) {
                        radioButton2.setChecked(false);
                        statusRbReport = false

                    }
                R.id.radioButton2 ->
                    if (checked) {
                        radioButton1.setChecked(false);
                        statusRbReport = true
                    }
            }

        }
    }



    fun inVisivility(){
        radioButton1.visibility = View.GONE
        radioButton2.visibility = View.GONE
        textView19d.visibility = View.GONE
        docsimgasing.visibility = View.GONE
        btnsent.visibility = View.GONE
        docsimg.visibility = View.VISIBLE
    }
    fun visivility(){
        radioButton2.visibility = View.VISIBLE
        textView19d.visibility = View.VISIBLE
        btnsent.visibility = View.VISIBLE
        docsimg.visibility = View.GONE
        docsimgasing.visibility = View.VISIBLE
    }

    fun visivilityInfoPatient(){
        namep.visibility = View.VISIBLE
        dnid.visibility =  View.VISIBLE
        sexd.visibility =  View.VISIBLE
        aged.visibility =  View.VISIBLE
        nameptitle.visibility =  View.VISIBLE
        dnidtitle.visibility =  View.VISIBLE
        sexdtitle.visibility =  View.VISIBLE
        agedtitle.visibility =  View.VISIBLE
        cbpatient.visibility = View.VISIBLE

        lpnd.visibility =  View.GONE
        tvEmpty1nd.visibility =  View.GONE
        tvEmpty2nd.visibility =  View.GONE
    }
}