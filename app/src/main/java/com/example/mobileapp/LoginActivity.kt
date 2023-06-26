package com.example.mobileapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener


class LoginActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth
    private var authStateListener: AuthStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //record user login
        authStateListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            //if no login show login page else
            if (user == null) {
                setContentView(R.layout.activity_login)
            } else {
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
        auth= FirebaseAuth.getInstance()
    }


    fun login(view: View){
        //Login function
        val editTextEmailAddress  = findViewById<EditText>(R.id.editTextEmailAddress)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val email=editTextEmailAddress.text.toString()
        val password = editTextPassword.text.toString()
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{ exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()

        }
    }

    fun goToRegister(view:View){
        val intent= Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }


    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener!!)
    }

    override fun onStop() {
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener!!)
        }
        super.onStop()
    }
}