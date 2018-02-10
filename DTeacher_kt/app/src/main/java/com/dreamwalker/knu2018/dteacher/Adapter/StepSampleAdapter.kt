package com.dreamwalker.knu2018.dteacher.Adapter

import android.content.Context
import android.os.Bundle
import android.support.annotation.IntRange
import android.support.v4.app.FragmentManager

import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDrugFragment
import com.dreamwalker.knu2018.dteacher.Fragment.WriteBSTypeFragment
import com.dreamwalker.knu2018.dteacher.Fragment.WriteBSValueFragment
import com.dreamwalker.knu2018.dteacher.Fragment.WriteDateTimeFragment
import com.dreamwalker.knu2018.dteacher.R
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel

/**
 * Created by KNU2017 on 2018-02-07.
 */

class StepSampleAdapter(fm: FragmentManager, context: Context) : AbstractFragmentStepAdapter(fm, context) {

    override fun createStep(position: Int): Step? {
        when (position) {
            0 -> return WriteBSTypeFragment.newInstance("hill", "hi")
            1 -> return WriteBSValueFragment.newInstance("hill", "hi")
            2 -> return WriteDateTimeFragment.newInstance("hill", "hi")
        }
        return null
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getViewModel(@IntRange(from = 0) position: Int): StepViewModel {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        when (position) {
            0 -> return StepViewModel.Builder(context)
                    .setTitle("유형입력") //can be a CharSequence instead
                    .create()

            1 -> return StepViewModel.Builder(context)
                    .setTitle("혈당입력") //can be a CharSequence instead
                    .create()
            2 -> return StepViewModel.Builder(context)
                    .setTitle("시간입력") //can be a CharSequence instead
                    .create()
        }

        return StepViewModel.Builder(context)
                .setTitle("Tesk") //can be a CharSequence instead
                .create()
    }
}
