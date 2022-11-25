package com.example.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.ecommerce.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "test_firestore"
    }

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        // setContentView(R.layout.activity_main) forma basica
        // val editEmail = findViewById<EditText>(R.id.editEmail) forma basica

        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editEmail = binding.editEmail
        val editPassword = binding.editPassword
        val btnLogin = binding.btnLogin

        btnLogin.setOnClickListener {

            if (editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(
                    editEmail.text.toString(),
                    editPassword.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            //updateUI(null)
                        }
                    }
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }

        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload();
        }
    }

    private fun reload() {
        val inteint = Intent(this@MainActivity, MainActivityProfile::class.java)
        inteint.putExtra("name", "Fernando")
        startActivity(inteint)
    }


}