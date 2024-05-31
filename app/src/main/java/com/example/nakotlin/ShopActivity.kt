package com.example.nakotlin

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ShopActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var counter = 0
    private var multiplier = 1
    private var autoClickerEnabled = false
    private var autoClickerRate = 1
    private var discount = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        sharedPreferences = getSharedPreferences("clickerAppPrefs", MODE_PRIVATE)
        counter = sharedPreferences.getInt("counter", 0)
        multiplier = sharedPreferences.getInt("multiplier", 1)
        autoClickerEnabled = sharedPreferences.getBoolean("autoClickerEnabled", false)
        autoClickerRate = sharedPreferences.getInt("autoClickerRate", 1)
        discount = sharedPreferences.getFloat("discount", 1.0f).toDouble()

        val backButton: Button = findViewById(R.id.backButton)
        val buyMultiplierButton: Button = findViewById(R.id.buyMultiplierButton)
        val buyTripleButton: Button = findViewById(R.id.buyTripleButton)
        val buyAutoClickerButton: Button = findViewById(R.id.buyAutoClickerButton)
        val buyAutoClickerUpgradeButton: Button = findViewById(R.id.buyAutoClickerUpgradeButton)
        val buyDiscountButton: Button = findViewById(R.id.buyDiscountButton)
        val counterTextView: TextView = findViewById(R.id.counterTextView)

        counterTextView.text = "Clicks: $counter"

        backButton.setOnClickListener {
            finish()
        }

        buyMultiplierButton.setOnClickListener {
            val cost = (100 * discount).toInt()
            if (counter >= cost) {
                counter -= cost
                multiplier *= 2
                counterTextView.text = "Clicks: $counter"
                saveData()
            }
        }

        buyTripleButton.setOnClickListener {
            val cost = (200 * discount).toInt()
            if (counter >= cost) {
                counter -= cost
                multiplier *= 3
                counterTextView.text = "Clicks: $counter"
                saveData()
            }
        }

        buyAutoClickerButton.setOnClickListener {
            val cost = (300 * discount).toInt()
            if (counter >= cost) {
                counter -= cost
                autoClickerEnabled = true
                counterTextView.text = "Clicks: $counter"
                saveData()
                startAutoClicker()
            }
        }

        buyAutoClickerUpgradeButton.setOnClickListener {
            val cost = (200 * discount).toInt()
            if (counter >= cost) {
                counter -= cost
                autoClickerRate *= 2
                counterTextView.text = "Clicks: $counter"
                saveData()
            }
        }

        buyDiscountButton.setOnClickListener {
            val cost = (100 * discount).toInt()
            if (counter >= cost) {
                counter -= cost
                discount *= 0.9
                counterTextView.text = "Clicks: $counter"
                saveData()
            }
        }
    }

    private fun saveData() {
        val editor = sharedPreferences.edit()
        editor.putInt("counter", counter)
        editor.putInt("multiplier", multiplier)
        editor.putBoolean("autoClickerEnabled", autoClickerEnabled)
        editor.putInt("autoClickerRate", autoClickerRate)
        editor.putFloat("discount", discount.toFloat())
        editor.apply()
    }

    private fun startAutoClicker() {
        if (autoClickerEnabled) {
            val handler = android.os.Handler()
            handler.post(object : Runnable {
                override fun run() {
                    counter += autoClickerRate
                    saveData()
                    val counterTextView: TextView = findViewById(R.id.counterTextView)
                    counterTextView.text = "Clicks: $counter"
                    handler.postDelayed(this, 1000)
                }
            })
        }
    }
}
