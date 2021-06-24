package com.example.corakshakscanner

import android.R.attr.password
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    lateinit var adminId : TextInputLayout
    lateinit var adminEmail : TextInputLayout
    lateinit var adminPassword : TextInputLayout
    lateinit var firebaseDBRef: DatabaseReference
     var id: String = ""
        private var loadingBar: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        adminId = findViewById(R.id.admin_id)
        adminEmail = findViewById(R.id.admin_email)
        adminPassword = findViewById(R.id.admin_password)
        mAuth = FirebaseAuth.getInstance();
        firebaseDBRef = FirebaseDatabase.getInstance().getReference("admin")
        loadingBar=  ProgressDialog(this);
        val login : Button = findViewById(R.id.login)
        login.setOnClickListener {
            loginUser()

        }

    }

    private fun loginUser() {
        id= adminId.editText?.text.toString()
        if (id.isEmpty()){
            adminId.error = "Enter Admin Id"
            return
        }
        firebaseDBRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(id).exists())
                    signIn()
                else
                    Toast.makeText(this@LoginActivity, "Admin Id is not correct", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    private fun signIn(){
        val email_entered: String = adminEmail.editText?.text.toString()
        val password_entered: String = adminPassword.editText?.text.toString()
        if (email_entered.isEmpty()) {
            adminEmail.error = "Enter Email"
            return
        }
        if (email_entered.isEmpty()) {
            adminPassword.error = "Enter Password"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_entered).matches()) {
            adminEmail.error = "Invalid Email"
            return
        }
        loadingBar?.setTitle("Checking Credential")
        loadingBar?.setMessage("please Wait")
        loadingBar?.setCanceledOnTouchOutside(false)
        loadingBar?.show()

        mAuth!!.signInWithEmailAndPassword(email_entered, password_entered)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth!!.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    loadingBar?.dismiss()
                    val e = task.exception.toString()
                    Toast.makeText(
                        this, "Credentials Does Not Match",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // ...
            }

    }
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            loadingBar?.dismiss()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }
}