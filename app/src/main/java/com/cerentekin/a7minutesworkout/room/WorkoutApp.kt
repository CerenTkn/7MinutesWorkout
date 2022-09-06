package com.cerentekin.a7minutesworkout.room

import android.app.Application
import com.cerentekin.a7minutesworkout.room.HistoryDatabase

class WorkoutApp : Application() {

    val db by lazy {
        HistoryDatabase.getInstance(this)
    }

}