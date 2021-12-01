package com.sophia.saveandshowmyactivitytoday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sophia.saveandshowmyactivitytoday.databinding.ActivitySettingGoalsBinding

class SettingGoalsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingGoalsBinding
    private lateinit var goalText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClick()
    }

    private fun setOnClick() {
        binding.saveBtn.setOnClickListener {
            goalText = binding.goalEt.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("goal",goalText)
            startActivity(intent)
        }
    }
}