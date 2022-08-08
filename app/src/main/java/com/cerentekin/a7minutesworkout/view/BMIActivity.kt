package com.cerentekin.a7minutesworkout.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cerentekin.a7minutesworkout.R
import com.cerentekin.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }
    //a variable to hold a value to make a selected view visible
    private var currentVisibleView: String = METRIC_UNITS_VIEW
    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBmiActivity)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding.toolbarBmiActivity.setNavigationOnClickListener{
            onBackPressed()
        }
        makeVisibleMetricUnitsView()

        binding.rgUnits.setOnCheckedChangeListener { _, checkedId: Int ->

            // Here if the checkId is METRIC UNITS view then make the view visible else US UNITS view.
             if (checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }
        }

        binding.btnCalculateUnits.setOnClickListener {
            calculateUnits()
        }
    }
    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUnitHeight.visibility = View.VISIBLE
        binding.tilUsMetricUnitWeight.visibility = View.INVISIBLE
        binding.tilMetricUsUnitHeightFeet.visibility = View.INVISIBLE
        binding.tilMetricUsUnitHeightInch.visibility = View.INVISIBLE
        binding.llDiplayBMIResult.visibility = View.INVISIBLE
    }
    private fun makeVisibleUsUnitsView (){
        currentVisibleView = US_UNITS_VIEW
        binding.tilMetricUnitHeight.visibility = View.INVISIBLE
        binding.tilMetricUnitWeight.visibility = View.INVISIBLE
        binding.tilUsMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUsUnitHeightInch.visibility = View.VISIBLE
        binding.tilMetricUsUnitHeightFeet.visibility = View.VISIBLE

        binding.etUsMetricUnitWeight.text!!.clear()
        binding.etUsMetricUnitHeightInch.text!!.clear()
        binding.etUsMetricUnitHeightFeet.text!!.clear()

        binding.llDiplayBMIResult.visibility = View.INVISIBLE
    }
    private fun displayBMIResult(bmi : Float){
        val bmiLabel : String
        val bmiDescription : String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(15f)> 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if (bmi.compareTo(16f)> 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        }else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(bmi,30f)<= 0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) < 0){
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        }else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding.llDiplayBMIResult.visibility = View.VISIBLE
        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription
    }
    private fun validateMetricUnits():Boolean{
        var isValid = true

        if (binding.etMetricUnitWeight.text.toString().isEmpty()){
            isValid = false
        }else if (binding.etMetricUnitHeight.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }
    private fun calculateUnits(){
        if (currentVisibleView == METRIC_UNITS_VIEW){
            if (validateMetricUnits()){
                val heightValue: Float = binding.etMetricUnitHeight.text.toString().toFloat()/100
                val weightValue: Float = binding.etMetricUnitWeight.text.toString().toFloat()
                val bmi = weightValue / (heightValue*heightValue)

                displayBMIResult(bmi)
                //TODO display BMI results
            }else{
                Toast.makeText(this,"Please enter valid values.", Toast.LENGTH_SHORT).show()
            }
        }else{
            if (validateUsUnits()){

                val usUnitHeightValueFeet: String =
                    binding.etUsMetricUnitHeightFeet.text.toString()// Height Feet value entered in EditText component.
                val usUnitHeightValueInch: String =
                    binding.etUsMetricUnitHeightInch.text.toString() // Height Inch value entered in EditText component.
                val usUnitWeightValue: Float = binding.etUsMetricUnitWeight.text.toString()
                    .toFloat() // Weight value entered in EditText component.
                //Here the Height Feet and Inch values are merged and multiplied by 12 for  convert
                val heightValue = usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat()*12
                val bmi = 703 *  (usUnitWeightValue / (heightValue * heightValue))

                displayBMIResult(bmi)
            }else{
                Toast.makeText(this,"Please enter valid values.", Toast.LENGTH_SHORT).show()
            }



        }
    }
    private fun validateUsUnits():Boolean{
        var isValid = true

        when{
            binding.etUsMetricUnitWeight.text.toString().isEmpty()->{
                isValid = false
            }
            binding.etUsMetricUnitHeightFeet.text.toString().isEmpty()->{
                isValid = false
            }
            binding.etUsMetricUnitHeightInch.text.toString().isEmpty()->{
                isValid = false
            }
        }
        return isValid
    }
}