package com.dreamwalker.knu2018.dteacher.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import com.dreamwalker.knu2018.dteacher.R
import com.joaquimley.faboptions.FabOptions

class WorkoutActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        val fabOptions = findViewById<View>(R.id.fab_options) as FabOptions
        fabOptions.setButtonsMenu(R.menu.sample_menu)
    }

    override fun onClick(v: View) {
        Log.e(TAG, "onClick: " + v.id)
    }

    companion object {
        private val TAG = "WorkoutActivity"
    }
}
