package com.cerentekin.a7minutesworkout.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cerentekin.a7minutesworkout.R
import com.cerentekin.a7minutesworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistoryActivity)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding.toolbarHistoryActivity.setNavigationOnClickListener{
            onBackPressed()
        }
    }
}