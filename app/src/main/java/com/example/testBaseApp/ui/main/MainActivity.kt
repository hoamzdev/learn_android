package com.example.testBaseApp.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.testBaseApp.R
import com.example.testBaseApp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleClick()

    }

    private fun handleClick(){
        val btn = findViewById<AppCompatButton>(R.id.btnClick)
        btn.setOnClickListener {
            // Handle button click
            //su kien su ly o day
            fetchApi()
        }
    }

    private fun fetchApi(){
        Toast.makeText(this, "Fetch API", Toast.LENGTH_SHORT).show()
    }

}

