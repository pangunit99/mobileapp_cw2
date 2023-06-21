package com.example.mobileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class upviewActivity : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var binding:ActivityMainBinding
    private lateinit var productArratlist:ArrayList<product_data>
    private lateinit var view1:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upview)
        binding = ActivityMainBinding.inflate(layoutInflater)
        view1 = findViewById<RecyclerView>(R.id.view1)
        view1.layoutManager = LinearLayoutManager(this)
        view1.setHasFixedSize(true)
        productArratlist = arrayListOf()
        fetchdata()
    }

    fun fetchdata(){
        db = FirebaseDatabase.getInstance().getReference("product")
        db.child("save").get().addOnSuccessListener { documents->
            for (doc in documents.children){

                val product = doc.getValue(product_data::class.java)
                productArratlist.add(product!!)

            }

            view1.adapter= viewAdapter(this,productArratlist)


        }

    }
}