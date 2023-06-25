package com.example.mobileapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [nextmap.newInstance] factory method to
 * create an instance of this fragment.
 */
class nextmap : Fragment() {
    // TODO: Rename and change types of parameters
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
            val sydney = LatLng(22.31386131352032, 114.17081351822735)
            mMap.addMarker(MarkerOptions().position(sydney).title("Grocery Store"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
        getActivity()?.getFragmentManager()?.popBackStack();

        return view

    }
}