package com.example.mobileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobileapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference

class Addproduct : AppCompatActivity() {

    var sImg:String?=""
    private lateinit var db:DatabaseReference
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_addproduct)


    }

    fun insert_data(){

    }

    fun insert_img(){

    }
}