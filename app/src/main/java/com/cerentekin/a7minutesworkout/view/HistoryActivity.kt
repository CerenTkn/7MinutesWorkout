package com.cerentekin.a7minutesworkout.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.cerentekin.a7minutesworkout.R
import com.cerentekin.a7minutesworkout.databinding.ActivityHistoryBinding
import com.cerentekin.a7minutesworkout.room.HistoryDao
import com.cerentekin.a7minutesworkout.room.WorkoutApp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        val dao =(application as WorkoutApp).db.historyDao()
        getAllCompletedDates(dao)
    }
    private fun getAllCompletedDates(historyDao: HistoryDao){
        Log.e("Date: ","getAllCompletedDates run")
        lifecycleScope.launch{
            historyDao.fetchAllDates().collect{ allCompletedDatesList ->
                for (i in allCompletedDatesList){
                    Log.e("Date: ", "" + i.date)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}