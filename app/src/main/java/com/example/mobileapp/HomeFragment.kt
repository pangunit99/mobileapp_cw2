package com.example.mobileapp

import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import java.util.*


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
        mGeocoder = Geocoder(context)
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
                    // permissionOk = true;
                    tv1!!.text = "permission granted"
                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                    // permissionOk = true;
                    tv1!!.text = "permission granted"
                } else {
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
                        showlocation()
                    } }, Looper.getMainLooper())

        return view;
    }


    private fun showlocation(){
        val Neareststore = view?.findViewById<TextView>(R.id.Neareststore)
        //setting the store lat lon in sheung wan
        val shop1_lat = 22.28619944225652
        val shop1_lon = 114.15242235869407

        //setting the store lat lon in mongkok
        val shop2_lat = 22.3138613135203
        val shop2_lon = 114.17081351822735

        //cal user
        val theta: Double = mLastLocation!!.longitude - shop1_lon

        val shop2: Double = mLastLocation!!.longitude - shop2_lon

        var shop3 = (Math.sin(deg2rad(mLastLocation!!.latitude))
                * Math.sin(deg2rad(shop1_lat))
                + (Math.cos(deg2rad(mLastLocation!!.latitude))
                * Math.cos(deg2rad(shop1_lat))
                * Math.cos(deg2rad(shop2))))

        var dist = (Math.sin(deg2rad(mLastLocation!!.latitude))
                * Math.sin(deg2rad(shop1_lat))
                + (Math.cos(deg2rad(mLastLocation!!.latitude))
                * Math.cos(deg2rad(shop1_lat))
                * Math.cos(deg2rad(theta))))

        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515

        shop3 = Math.acos(shop3)
        shop3 = rad2deg(shop3)
        shop3 = shop3 * 60 * 1.1515



        //show the store location
        if(dist < shop3){
            try{
                val addresses = mGeocoder!!.getFromLocation(
                    shop1_lat,shop1_lon,1
                )

                if(addresses!!.size ==1){
                    val address = addresses[0]
                    val addressLines = StringBuilder()
                    if(address.maxAddressLineIndex > 0)
                        for (i in 0 until address.maxAddressLineIndex){
                            addressLines.append("""${address.getAddressLine(i)}""".trimIndent())
                        }else {
                        addressLines.append(address.getAddressLine(0))
                    }
                    Neareststore!!.text = addressLines
                }
            }catch(e:Exception){

            }
        }else{
            try{
                val addresses = mGeocoder!!.getFromLocation(
                    shop2_lat,shop2_lon,1
                )

                if(addresses!!.size ==1){
                    val address = addresses[0]
                    val addressLines = StringBuilder()
                    if(address.maxAddressLineIndex > 0)
                        for (i in 0 until address.maxAddressLineIndex){
                            addressLines.append("""${address.getAddressLine(i)}""".trimIndent())
                        }else {
                        addressLines.append(address.getAddressLine(0))
                    }
                    Neareststore!!.text = addressLines
                }
            }catch(e:Exception){

            }
        }

    }
    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }



}