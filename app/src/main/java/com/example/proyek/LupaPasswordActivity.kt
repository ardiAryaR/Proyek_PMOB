package com.example.proyek

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class LupaPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lupa_password)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val btnSend = findViewById<MaterialButton>(R.id.btnSend)

        auth = FirebaseAuth.getInstance()

        btnSend.setOnClickListener {

            val email = etEmail.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Email tidak valid"
                return@setOnClickListener
            }

            btnSend.isEnabled = false

            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    startActivity(Intent(this, CheckEmailActivity::class.java))
                    finish()
                }

                .addOnFailureListener {
                    btnSend.isEnabled = true
                    Toast.makeText(
                        this,
                        "Gagal mengirim link reset password",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }
}
