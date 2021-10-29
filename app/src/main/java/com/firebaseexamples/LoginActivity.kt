package com.firebaseexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        txtEmail = findViewById<EditText>(R.id.txtEMailLogin)
        txtPassword = findViewById<EditText>(R.id.txtPasswordLogin)

        btnRegister.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener{
            logIn();
        }
    }

    private fun validateInputs() : Boolean{
        val email : String = txtEmail.text.toString().trim{it <= ' '}
        val password : String = txtPassword.text.toString().trim{it <= ' '}

        if(email.isEmpty()) {
            txtEmail.error = "Please enter an eMail"
            return false
        }

        if(password.isEmpty()) {
            txtPassword.error = "Please enter a password"
            return false
        }

        return true
    }

    private fun logIn() {
        val email : String = txtEmail.text.toString().trim{it <= ' '}
        val password : String = txtPassword.text.toString().trim{it <= ' '}

        if(!validateInputs()) {
            return;
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(
            OnCompleteListener<AuthResult> { task ->
                if(task.isSuccessful) {
                    val firebaseUser : FirebaseUser = task.result!!.user!!

                    Toast.makeText(this@LoginActivity, "Logged In", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity, LoggedInActivity::class.java)
                    intent.putExtra("user_id", firebaseUser.uid)
                    intent.putExtra("display_name", firebaseUser.displayName)
                    intent.putExtra("email", firebaseUser.email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, task.exception!!.message.toString(), Toast.LENGTH_SHORT)
                }
            }
        )
    }
}