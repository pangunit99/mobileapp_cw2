package com.example.mobileapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

// TODO: Rename parameter arguments, choose names that match

class fragment_showmap : Fragment() {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_showmap, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync {
            mMap = it
            // Add a marker in Sydney and move the camera
            val sydney = LatLng(22.28619944225652, 114.15242235869407)
            mMap.addMarker(MarkerOptions().position(sydney).title("Grocery Store"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
        getActivity()?.getFragmentManager()?.popBackStack();

        return view

    }



}