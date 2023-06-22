package com.example.mobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.reflect.Array
import kotlin.math.log

class upviewActivity : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var binding:ActivityMainBinding
    private lateinit var productArratlist:ArrayList<product_data>
    private lateinit var view1:RecyclerView
    val list = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upview)
        binding = ActivityMainBinding.inflate(layoutInflater)
        view1 = findViewById<RecyclerView>(R.id.view1)
        view1.layoutManager = LinearLayoutManager(this)
        view1.setHasFixedSize(true)
        view1.itemAnimator = DefaultItemAnimator()
        productArratlist = arrayListOf()
        fetchdata()

        //recyclerView swip function delete record
        val helper = ItemTouchHelper(object  : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if(direction == ItemTouchHelper.RIGHT) {
                    val item = viewHolder.adapterPosition
                    productArratlist.removeAt(item)
                    list.removeAt(item)
                    view1.adapter?.notifyDataSetChanged()
                }
            }

        })
        helper.attachToRecyclerView(view1)

    }


    //get firebaseDatabase data put on RecyclerView
    fun fetchdata(){
        db = FirebaseDatabase.getInstance().getReference("product")
        Log.d("GET DATA", db.child("save").key.toString())
        db.child("save").get().addOnSuccessListener { documents->
            for (doc in documents.children){
                val ky = doc.key.toString()
                list.add(ky)

                val product = doc.getValue(product_data::class.java)
                productArratlist.add(product!!)

            }
            var adapter = viewAdapter(this,productArratlist)
            view1.adapter= adapter

            //RecyclerView click event
            adapter.setOnItemClickListener(object :viewAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    Toast.makeText(this@upviewActivity,"Youclick on item key : ${list[position]}",Toast.LENGTH_SHORT).show()

                    //click product go to update page
                    val intent = Intent(this@upviewActivity, modifyActivity::class.java)
                    intent.putExtra("key", "${list[position]}")
                    startActivity(intent)
                    finish()

                }
            })


        }

    }
}