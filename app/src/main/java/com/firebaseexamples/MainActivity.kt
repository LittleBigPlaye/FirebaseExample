package com.firebaseexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val btnGoToLogin = findViewById<Button>(R.id.btnGoToLogin);
        val btnGoToDatabase = findViewById<Button>(R.id.btnGoToDatabase);

        btnGoToDatabase.setOnClickListener {
            val intent = Intent(this, RealTimeDatabaseExample::class.java);
            startActivity(intent);
        }

        btnGoToLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent);
        }

    }


}