package com.example.mobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [userView.newInstance] factory method to
 * create an instance of this fragment.
 */
class userView : Fragment() {

    private lateinit var db: DatabaseReference
    private lateinit var productArratlist:ArrayList<product_data>
    private lateinit var view1: RecyclerView
    val list = arrayListOf<String>()
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater!!.inflate(R.layout.fragment_user_view,container,false)

        view1 = view.findViewById<RecyclerView>(R.id.uview)
        view1.layoutManager = LinearLayoutManager(view.context)
        view1.setHasFixedSize(true)
        view1.itemAnimator = DefaultItemAnimator()
        productArratlist = arrayListOf()

        db = FirebaseDatabase.getInstance().getReference("product")
        Log.d("GET DATA", db.child("save").key.toString())
        db.child("save").get().addOnSuccessListener { documents->
            for (doc in documents.children){
                val ky = doc.key.toString()
                list.add(ky)

                val product = doc.getValue(product_data::class.java)
                productArratlist.add(product!!)

            }
            var adapter = userviewAdapter(view.context,productArratlist)
            view1.adapter= adapter

            //RecyclerView click event
            adapter.setOnItemClickListener(object :userviewAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {

                }
            })


        }

        return view
    }



}