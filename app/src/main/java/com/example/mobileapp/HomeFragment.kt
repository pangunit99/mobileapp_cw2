package com.example.mobileapp

import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import java.text.DateFormat
import java.util.*
import java.util.jar.Manifest


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var  client: FusedLocationProviderClient
    private lateinit var button:Button
    private lateinit var tv1:TextView
    private lateinit var tv2:TextView

    protected lateinit var mLastLocation: Location
    protected lateinit var mLocationRequest: LocationRequest
    protected lateinit var mGeocoder: Geocoder
    protected lateinit var mLocationProvider: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private fun checkpermissiom():Boolean{

        return false
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_home,container,false)
        val tv1 = view.findViewById<TextView>(R.id.tv_latitude)
        val tv2 = view.findViewById<TextView>(R.id.tv_longitude)
        val button = view.findViewById<Button>(R.id.bt_location)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            ActivityResultCallback<Map<String, Boolean>> { result: Map<String, Boolean?> ->
                val fineLocationGranted = result.getOrDefault(
                    android.Manifest.permission.ACCESS_FINE_LOCATION, false
                )
                val coarseLocationGranted = result.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, false
                )
                if (fineLocationGranted != null && fineLocationGranted) {
                    // Precise location access granted.
                    // permissionOk = true;
                    tv1!!.text = "permission granted"
                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                    // Only approximate location access granted.
                    // permissionOk = true;
                    tv1!!.text = "permission granted"
                } else {
                    // permissionOk = false;
                    // No location access granted.
                    tv1!!.text = "permission not granted"
                }

            }
        )
        locationPermissionRequest.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 10
        mLocationRequest!!.fastestInterval = 5
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY


            mLocationProvider = LocationServices.getFusedLocationProviderClient(view.context)
            if (ActivityCompat.checkSelfPermission(
                    view.context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    view.context,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {



            }
            mLocationProvider.requestLocationUpdates(mLocationRequest,
                object : LocationCallback(){
                    override fun onLocationResult(result: LocationResult) {
                        mLastLocation = result.lastLocation
                        tv1!!.text = mLastLocation!!.latitude.toString()
                        tv2!!.text = mLastLocation!!.longitude.toString()

                    } }, Looper.getMainLooper())


        return view;
    }



}