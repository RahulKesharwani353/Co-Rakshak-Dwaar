package com.example.corakshak

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*

class VerificationActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var currentuser : String
    private var loadingBar: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        auth= FirebaseAuth.getInstance()

        loadingBar=  ProgressDialog(this);
        val storedVerificationId=intent.getStringExtra("storedVerificationId")

//        Reference
        val verify=findViewById<CardView>(R.id.verifyBtn)
        val otpGiven=findViewById<EditText>(R.id.id_otp)


        verify.setOnClickListener{


            var otp=otpGiven.text.toString().trim()
            if(!otp.isEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                loadingBar?.setTitle("Verifying")
                loadingBar?.setMessage("please Wait")
                loadingBar?.setCanceledOnTouchOutside(false)
                loadingBar?.show()
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    loadingBar?.dismiss()
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finishAffinity()
// ...
                } else {
// Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
                        loadingBar?.dismiss()
                        Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }


}