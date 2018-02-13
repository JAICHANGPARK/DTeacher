package com.dreamwalker.knu2018.dteacher.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.dreamwalker.knu2018.dteacher.Fragment.DiaryDrugFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.WriteBSTypeFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.WriteBSValueFragment;
import com.dreamwalker.knu2018.dteacher.Fragment.WriteDateTimeFragment;
import com.dreamwalker.knu2018.dteacher.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * Created by KNU2017 on 2018-02-07.
 */

public class StepSampleAdapter extends AbstractFragmentStepAdapter{

    public StepSampleAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        switch (position){
            case 0:
                return WriteBSTypeFragment.newInstance("hill","hi");
            case 1:
                return WriteBSValueFragment.newInstance("hill","hi");
            case 2:
                return WriteDateTimeFragment.newInstance("hill","hi");
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        switch (position){
            case 0:
                return new StepViewModel.Builder(context)
                        .setTitle("유형입력") //can be a CharSequence instead
                        .create();

            case 1:
                return new StepViewModel.Builder(context)
                        .setTitle("혈당입력") //can be a CharSequence instead
                        .create();
            case 2:
                return new StepViewModel.Builder(context)
                        .setTitle("시간입력") //can be a CharSequence instead
                        .create();

        }

        return new StepViewModel.Builder(context)
                .setTitle("Tesk") //can be a CharSequence instead
                .create();
    }
}
