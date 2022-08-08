package com.cerentekin.a7minutesworkout

import com.cerentekin.a7minutesworkout.model.ExerciseModel

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingJacks = ExerciseModel(
            1,"Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false
        )
        exerciseList.add(jumpingJacks)

        val highKneesRunningInPlace = ExerciseModel(
            2,"High Knees Running In Place",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false
        )
        exerciseList.add(highKneesRunningInPlace)

        val lunge = ExerciseModel(
            3,"Lunge",
            R.drawable.ic_lunge,
            false,
            false
        )
        exerciseList.add(lunge)

        val wallSit = ExerciseModel(
            4,"Wall Sit",
            R.drawable.ic_wall_sit,
            false,
            false
        )
        exerciseList.add(wallSit)

        val squat = ExerciseModel(
            5,"Squat",
            R.drawable.ic_squat,
            false,
            false
        )
        exerciseList.add(squat)

        val stepUpOntoChair = ExerciseModel(
            6,
            "Step Up Onto Chair",
            R.drawable.ic_step_up_onto_chair,
            false,
            false
        )
        exerciseList.add(stepUpOntoChair)

        val tricepsDipOnChair = ExerciseModel(
            7,"Triceps Dip Onto Chair",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false
        )
        exerciseList.add(tricepsDipOnChair)

        val plank = ExerciseModel(
            8, "Plank",
            R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)

        val sidePlank = ExerciseModel(
            9,"Side Plank",
            R.drawable.ic_side_plank,
            false,
            false
        )
        exerciseList.add(sidePlank)

        val pushUp = ExerciseModel(
            10, "Push Up",
            R.drawable.ic_push_up,
            false,
            false
        )
        exerciseList.add(pushUp)

        val pushUpAndRotatiton = ExerciseModel(
            11,"Push Up and Rotation",
            R.drawable.ic_push_up_and_rotation,
            false,
            false
        )
        exerciseList.add(pushUpAndRotatiton)

        val abdominalCrunch = ExerciseModel(
            12,"Abdominal Crunch",
            R.drawable.ic_abdominal_crunch,
            false,
            false
        )
        exerciseList.add(abdominalCrunch)

        return exerciseList
    }
}