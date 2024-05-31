package com.example.nakotlin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private var multiplier = 1
    private var autoClickerEnabled = false
    private var autoClickerRate = 1
    private lateinit var sharedPreferences: SharedPreferences //sdfdsfds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("clickerAppPrefs", MODE_PRIVATE)
        counter = sharedPreferences.getInt("counter", 0)
        multiplier = sharedPreferences.getInt("multiplier", 1)
        autoClickerEnabled = sharedPreferences.getBoolean("autoClickerEnabled", false)
        autoClickerRate = sharedPreferences.getInt("autoClickerRate", 1)

        val counterTextView: TextView = findViewById(R.id.counterTextView)
        val tapButton: Button = findViewById(R.id.tapButton)
        val shopButton: Button = findViewById(R.id.shopButton)
        val aboutButton: Button = findViewById(R.id.aboutButton)
        val contactButton: Button = findViewById(R.id.contactButton)

        counterTextView.text = counter.toString()

        tapButton.setOnClickListener {
            counter += multiplier
            counterTextView.text = counter.toString()
            saveCounter()
        }

        shopButton.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }

        aboutButton.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        contactButton.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }

        if (autoClickerEnabled) {
            startAutoClicker()
        }
    }

    override fun onResume() {
        super.onResume()
        // Update counter and multiplier when returning from shop
        counter = sharedPreferences.getInt("counter", 0)
        multiplier = sharedPreferences.getInt("multiplier", 1)
        val counterTextView: TextView = findViewById(R.id.counterTextView)
        counterTextView.text = counter.toString()
    }

    private fun saveCounter() {
        val editor = sharedPreferences.edit()
        editor.putInt("counter", counter)
        editor.apply()
    }

    private fun startAutoClicker() {
        val handler = android.os.Handler()
        handler.post(object : Runnable {
            override fun run() {
                counter += autoClickerRate
                saveCounter()
                val counterTextView: TextView = findViewById(R.id.counterTextView)
                counterTextView.text = counter.toString()
                handler.postDelayed(this, 1000)
            }
        })
    }
}
