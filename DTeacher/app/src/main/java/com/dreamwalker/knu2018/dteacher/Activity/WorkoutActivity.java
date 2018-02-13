package com.dreamwalker.knu2018.dteacher.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dreamwalker.knu2018.dteacher.R;
import com.joaquimley.faboptions.FabOptions;

public class WorkoutActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "WorkoutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        FabOptions fabOptions = (FabOptions) findViewById(R.id.fab_options);
        fabOptions.setButtonsMenu(R.menu.sample_menu);
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick: " + v.getId() );
    }
}
