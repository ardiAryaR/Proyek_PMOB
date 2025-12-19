package com.example.proyek

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirm = findViewById<EditText>(R.id.etConfirmPassword)
        val btnSignUp = findViewById<MaterialButton>(R.id.btnSignUp)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {

            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirm = etConfirm.text.toString()

            when {
                name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty() -> {
                    Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    etEmail.error = "Email tidak valid"
                    return@setOnClickListener
                }
                password != confirm -> {
                    Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            btnSignUp.isEnabled = false

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {

                    val uid = auth.currentUser!!.uid

                    val userMap = mapOf(
                        "uid" to uid,
                        "name" to name,
                        "email" to email
                    )

                    FirebaseDatabase.getInstance()
                        .getReference("users")
                        .child(uid)
                        .setValue(userMap)
                        .addOnSuccessListener {

                            val intent = Intent(this, LoginActivity::class.java)
                            intent.putExtra("SIGNUP_SUCCESS", true)
                            startActivity(intent)
                            finish()
                        }

                }

                .addOnFailureListener {
                    btnSignUp.isEnabled = true
                    Toast.makeText(
                        this,
                        "Gagal daftar: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
