package com.cerentekin.a7minutesworkout.view

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.cerentekin.a7minutesworkout.*
import com.cerentekin.a7minutesworkout.adapter.ExerciseStatusAdapter
import com.cerentekin.a7minutesworkout.databinding.ActivityExerciseBinding
import com.cerentekin.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import com.cerentekin.a7minutesworkout.model.ExerciseModel
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityExerciseBinding
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimerDuration:Long = 1
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseTimerDuration: Long = 30
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExercise)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        exerciseList = Constants.defaultExerciseList()

        tts = TextToSpeech(this,this)

        binding.toolbarExercise.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
        //super.onBackPressed()
    }
    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        //kullanıcı yes ya da noya tıklamak zorunda yoksa devam edemez.
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()

        }
        customDialog.show()
    }


    private fun setupExerciseStatusRecyclerView(){
        binding.rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun setupRestView(){
        try{
            val soundURI = Uri.parse(
                "android.resource://com.cerentekin.a7minutesworkout" + R.raw.press_start
            )
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false // Sets the player to be looping or non-looping.
            player?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }
        binding.flRestView.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.VISIBLE
        binding.tvExerciseName.visibility = View.INVISIBLE
        binding.flExerciseView.visibility = View.INVISIBLE
        binding.ivImage.visibility = View.INVISIBLE
        binding.tvUpComingExerciseName.visibility = View.VISIBLE
        binding.tvUpcomingLabel.visibility = View.VISIBLE

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding.tvUpComingExerciseName.text =
            exerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar()
    }
    private fun setupExerciseView(){
        binding.flRestView.visibility = View.INVISIBLE
        binding.tvTitle.visibility = View.INVISIBLE
        binding.tvExerciseName.visibility = View.VISIBLE
        binding.flExerciseView.visibility = View.VISIBLE
        binding.ivImage.visibility = View.VISIBLE
        binding.tvUpComingExerciseName.visibility = View.INVISIBLE
        binding.tvUpcomingLabel.visibility = View.INVISIBLE
        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
        setExerciseProgressBar()
    }
    private fun setRestProgressBar(){
        binding.progressBar.progress = restProgress
        restTimer = object :CountDownTimer(restTimerDuration*10000,1000){
            override fun onTick(p0: Long) {
                //One thing that should happen every tick, which means every 1000 milliseconds.
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10 - restProgress).toString()
            }
            override fun onFinish() {
                //One thing that should happen once the countdown timer is done, in my case,
            // after 10000 milliseconds.
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }

        }.start()
    }
    private fun setExerciseProgressBar() {
        binding.progressBarExercise.progress = exerciseProgress
        exerciseTimer = object :CountDownTimer(exerciseTimerDuration*1000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding.progressBarExercise.progress = 30 - exerciseProgress
                binding.tvTimerExercise.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList?.size!! - 1){
                   exerciseList!![currentExercisePosition].setIsSelected(false)
                   exerciseList!![currentExercisePosition].setIsCompleted(true)
                   exerciseAdapter!!.notifyDataSetChanged()
                   setupRestView()
               }else{
                   finish()
                   val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                   startActivity(intent)
               }
            }
        }.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        //Shutting down the Text to Speech feature when activity is destroyed
        if (tts == null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if( player != null){
            player!!.stop()
        }
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.ENGLISH)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The language specified is not supported!")
            }
        }else{
            Log.e("TTS","Initialization Failed!")
        }
    }
    private fun speakOut(text: String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }
}