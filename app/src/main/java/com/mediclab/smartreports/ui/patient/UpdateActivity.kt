package com.mediclab.smartreports.ui.patient

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.mediclab.smartreports.R
import com.mediclab.smartreports.data.sign.Api
import com.mediclab.smartreports.data.sign.CreateReportResponse
import com.mediclab.smartreports.data.sign.ResponseUpdateUser
import com.mediclab.smartreports.data.sign.UpdateUser
import com.mediclab.smartreports.ui.BaseActivity
import com.mediclab.smartreports.ui.doct.DatePickerActivity
import com.mediclab.smartreports.utils.Memory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateActivity : BaseActivity() {
    lateinit var btnupdate: Button
    private lateinit var age: EditText
    private lateinit var sex: EditText
    private  var sexSet: Boolean = false
    private lateinit var departament: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        bind()
        onClickEvents()
    }

    private fun bind() {
        //etUser = findViewById(R.id.txt_user)
        sex = findViewById(R.id.editTextTextPersonNamesex)
        age = findViewById(R.id.editTextTextPersonName2)
        departament = findViewById(R.id.editTextTextPersonName3)
        btnupdate = findViewById(R.id.update_button)

    }

    private fun onClickEvents() {
        btnupdate.setOnClickListener {
            val idpatient = Memory.id.toInt()

            if(sex.text.toString() == "hombre" || sex.text.toString() == "HOMBRE" || sex.text.toString() == "homb" || sex.text.toString() == "masculino" || sex.text.toString() == "macho"){
                sexSet = true
            }
            Api.retrofitService.updateUser(UpdateUser(sexSet,age.text.toString().toInt(),departament.text.toString(),idpatient)).enqueue(object :
                    Callback<ResponseUpdateUser> {
                    override fun onResponse(
                        call: Call<ResponseUpdateUser>,
                        response: Response<ResponseUpdateUser>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                if(it.status == true){
                                    showToast("Datos actualziados")

                                    goTo(HomePatientActivity::class.java, true)
                                }else{
                                    showToast("Error al enviar el reporte")
                                }
                            }
                        } else {
                        }
                    }
                    override fun onFailure(call: Call<ResponseUpdateUser>, t: Throwable) {

                    }
                })


            goTo(HomePatientActivity::class.java)
        }
    }
}