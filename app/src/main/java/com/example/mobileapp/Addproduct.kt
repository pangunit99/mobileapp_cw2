package com.example.mobileapp

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.mobileapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.Date
import java.util.Locale

class Addproduct : AppCompatActivity() {


    private lateinit var db:DatabaseReference
    private lateinit var binding: ActivityMainBinding
    lateinit var ImgerUri:Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_addproduct)


    }

    companion object {
        val REQUEST_IMAGE_CAPTURE = 1
        var REQUEST_SELECT_IMAGE = 2
    }

    //use camera to get the image
    fun onCameraClicked(v : View){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    //get the image on phone
    fun onAlbumClicked(v : View) {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_SELECT_IMAGE)
    }

    //load image on imageView
    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
        val imageView = findViewById<ImageView>(R.id.imageView)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            val bytes =ByteArrayOutputStream()
            //camera image bitmap to uri
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes)
            val path = MediaStore.Images.Media.insertImage(applicationContext.contentResolver,bitmap,"val",null)
            val uri:Uri = Uri.parse(path)
            ImgerUri = uri
            imageView.setImageBitmap(bitmap)
        } else if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK){
            val selectedImageUri = data?.data as Uri
            ImgerUri = selectedImageUri
            imageView.setImageURI(selectedImageUri)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    fun onInsert(v: View){
        // get all user input
        var itemName= findViewById<EditText>(R.id.edititem).getText().toString()
        var price = findViewById<EditText>(R.id.editprice).getText().toString()
        var date = findViewById<EditText>(R.id.editdate).getText().toString()

        //processing animation
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("inserting....")
        progressDialog.setCancelable(false)
        progressDialog.show()

        //get fireplace saveplace
        db =FirebaseDatabase.getInstance().getReference("product")
        //set the image name
        val formatter = SimpleDateFormat("yyyy_mm_dd_hh_mm_ss", Locale.getDefault())
        val now = Date();
        val fileName = formatter.format(now)
        val stroageReference = FirebaseStorage.getInstance().getReference("product/$fileName")

        //upload image to firebase
        stroageReference.putFile(ImgerUri).
                addOnSuccessListener {
                    Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show()
                    stroageReference.downloadUrl.addOnSuccessListener {
                        //put all information to database
                        val uri =it
                        val product_data = product_data()
                        product_data.itemName = itemName
                        product_data.Price = price
                        product_data.date = date
                        product_data.itemImg = uri.toString()
                        db.child("save").push().setValue(product_data)
                    }
                    if(progressDialog.isShowing) progressDialog.dismiss()
                    finish()

                }.addOnFailureListener{
                    if(progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()

        }




    }


}