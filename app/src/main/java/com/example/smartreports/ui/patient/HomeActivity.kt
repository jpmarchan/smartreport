package com.example.smartreports.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartreports.R
import com.example.smartreports.adapters.HistorPatientAdapter
import com.example.smartreports.ui.BaseActivity

data class Reports(val name: String = "", val image: String = "", val description: String = "")

class HomeActivity : BaseActivity() {
    lateinit var setName: TextView
    private var dataReports= ArrayList<Reports>()
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recycler = findViewById(R.id.recyclerView)
        setName = findViewById(R.id.setName)

        val bundle = intent.extras
        val email:String = bundle?.getString("email").toString()
        getUsers(email)
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )
        dataReports.add(
            Reports()
        )

        configurarRecycler()

    }
    private fun configurarRecycler(){
        val adapter = HistorPatientAdapter(dataReports, this)
        recycler.adapter = adapter
    }

    fun getUsers(email: String){

        db.collection("Users").document(email)
            .get().addOnSuccessListener {
                    documentSnapshot ->
                setName.text = documentSnapshot.get("name").toString()
            }
    }
}