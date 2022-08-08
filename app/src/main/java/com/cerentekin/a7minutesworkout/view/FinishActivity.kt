package com.cerentekin.a7minutesworkout.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cerentekin.a7minutesworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFinishActivity)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarFinishActivity.setNavigationOnClickListener{
            onBackPressed()
        }
        binding.btnFinish.setOnClickListener {
            finish()
        }


    }
}