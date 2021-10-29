package com.firebaseexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoggedInActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)

        val userId = intent.getStringExtra("user_id");
        val username = intent.getStringExtra("display_name");
        val email = intent.getStringExtra("email");

        val txtUsername = findViewById<TextView>(R.id.txtUserName);
        val txtUserId = findViewById<TextView>(R.id.txtUserId);
        val txtEmail = findViewById<TextView>(R.id.txtEmail);

        txtUserId.setText(userId);
        txtUsername.setText(username);
        txtEmail.setText(email);

        val btnLogout = findViewById<Button>(R.id.btnLogout);
        btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this@LoggedInActivity, "Logged Out", Toast.LENGTH_SHORT);
            startActivity(Intent(this@LoggedInActivity, LoginActivity::class.java));
            finish();
        }
    }
}