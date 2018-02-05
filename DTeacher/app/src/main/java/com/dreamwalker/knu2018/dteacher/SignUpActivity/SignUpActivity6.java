package com.dreamwalker.knu2018.dteacher.SignUpActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.R;
import com.eminayar.panter.PanterDialog;
import com.google.android.flexbox.FlexboxLayout;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipListener;
import info.hoang8f.widget.FButton;

public class SignUpActivity6 extends AppCompatActivity {
    private static final String TAG = "SignUpActivity6";
    private static final int DRUG_TOTAL = 25;

    @BindView(R.id.step_view)
    StepView setpview;
    @BindView(R.id.nextButton)
    FButton nextButton;

    List<String> selectDrug;
    ArrayList<String> userSignUpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Sign Up");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sign_up6);

        ButterKnife.bind(this);

        userSignUpInfo = new ArrayList<>();
        userSignUpInfo = getIntent().getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_5);

        init_chips();

        String[] mySteps = {"Name", "Email", "Phone Number", "Gender"};

        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        setpview.getState()
                .selectedTextColor(ContextCompat.getColor(this, R.color.fbutton_color_midnight_blue))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(this, R.color.fbutton_color_sun_flower))
                .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen.dp14))
                .selectedStepNumberColor(ContextCompat.getColor(this, R.color.fbutton_color_midnight_blue))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>() {{
                    add("필수 정보");
                    add("기본 정보");
                    add("당뇨 정보");
                    add("투약 정보");
                }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(4)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp1))
                .textSize(getResources().getDimensionPixelSize(R.dimen.sp14))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp16))
                // other state methods are equal to the corresponding xml attributes
                .commit();

        setpview.go(3, false);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();
                // TODO: 2018-02-03 내가 짜도 참 괜찮은 코드
                for (int i = 0; i < selectDrug.size(); i++){
                    if (!selectDrug.get(i).contains("null")){
                        stringBuilder.append(selectDrug.get(i));
                        stringBuilder.append(",");
                    }
                }

                userSignUpInfo.add(stringBuilder.toString());
                Toast.makeText(SignUpActivity6.this, "Result " + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SignUpDoneActivity.class);
                intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_6,userSignUpInfo);
                intent.putExtra(IntentConst.SIGNUP_REGISTER_TYPE, 1);
                startActivity(intent);
                finish();
            }
        });

//        verticalStepperForm = (VerticalStepperFormLayout) findViewById(R.id.vertical_stepper_form);
//
//        // Setting up and initializing the form
//        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, this)
//                .primaryColor(colorPrimary)
//                .primaryDarkColor(colorPrimaryDark)
//                .displayBottomNavigation(true) // It is true by default, so in this case this line is not necessary
//                .init();
//    }

    /*@Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        switch (stepNumber) {
            case 0:
                view = createNameStep();
                break;
            case 1:
                view = createEmailStep();
                break;
            case 2:
                view = createPhoneNumberStep();
                break;
            case 3:
                view = createGenderStep();
        }
        return view;
    }

    private View createNameStep() {
        // Here we generate programmatically the view that will be added by the system to the step content layout
        name = new EditText(this);
        name.setSingleLine(true);
        name.setHint("Your name");

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTitleStep(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (checkTitleStep(v.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }
                return false;
            }
        });
        return name;
    }

    private View createEmailStep() {
        // In this case we generate the view by inflating a XML file
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout emailLayoutContent = (LinearLayout) inflater.inflate(R.layout.email_step_layout, null, false);
        email = (EditText) emailLayoutContent.findViewById(R.id.email);
        return emailLayoutContent;
    }

    private View createPhoneNumberStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout phoneLayoutContent = (LinearLayout) inflater.inflate(R.layout.phone_step_layout, null, false);
        return phoneLayoutContent;
    }

    private View createGenderStep() {
        // In this case we generate the view by inflating a XML file
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout genderLayoutContent = (LinearLayout) inflater.inflate(R.layout.gender_step_layout, null, false);
       //stickySwitch = (StickySwitch) genderLayoutContent.findViewById(R.id.sticky_switch);
        gender = (EditText) genderLayoutContent.findViewById(R.id.gender);
        return genderLayoutContent;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case 0:
                checkTitleStep(name.getText().toString());
                break;
            //checkName();
            //break;
            case 1:
                checkTitleStep(email.getText().toString());
                break;
            case 2:
                // As soon as the phone number step is open, we mark it as completed in order to show the "Continue"
                // button (We do it because this field is optional, so the user can skip it without giving any info)
                verticalStepperForm.setStepAsCompleted(2);
                // In this case, the instruction above is equivalent to:
                // verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 3:
                checkTitleStep(gender.getText().toString());
                //checkGender();
                break;
        }
    }

    private void checkName() {
        if (name.length() >= 3 && name.length() <= 40) {
            verticalStepperForm.setActiveStepAsCompleted();
        } else {
            // This error message is optional (use null if you don't want to display an error message)
            String errorMessage = "The name must have between 3 and 40 characters";
            verticalStepperForm.setActiveStepAsUncompleted(errorMessage);
        }
    }

    private void checkEmail() {
        if (email.length() >= 3 && email.length() <= 40) {
            verticalStepperForm.setActiveStepAsCompleted();
        } else {
            // This error message is optional (use null if you don't want to display an error message)
            String errorMessage = "The name must have between 3 and 40 characters";
            verticalStepperForm.setActiveStepAsUncompleted(errorMessage);
        }
    }


    private void checkGender() {

//        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
//            @Override
//            public void onSelectedChange(StickySwitch.Direction direction, String s) {
//                //Toast.makeText(SignUpActivity6.this, ""+ direction.name() + ", Current Text : " + s, Toast.LENGTH_SHORT).show();
//                Log.e("SignUpActivity6", "Now Selected : " + direction.name() + ", Current Text : " + s);
//                verticalStepperForm.setStepAsCompleted(3);
//            }
//        });*/
        // }


   /* @Override
    public void sendData() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.vertical_form_stepper_form_sending_data_message));
        //executeDataSending();

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();

    }

    private boolean checkTitleStep(String title) {
        boolean titleIsCorrect = false;

        if (title.length() >= MIN_CHARACTERS_TITLE) {
            titleIsCorrect = true;

            verticalStepperForm.setActiveStepAsCompleted();
            // Equivalent to: verticalStepperForm.setStepAsCompleted(TITLE_STEP_NUM);

        } else {
            String titleErrorString = getResources().getString(R.string.error_title_min_characters);
            String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);
            verticalStepperForm.setActiveStepAsUncompleted(titleError);
            // Equivalent to: verticalStepperForm.setStepAsUncompleted(TITLE_STEP_NUM, titleError);
        }
        return titleIsCorrect;
    }

    // CONFIRMATION DIALOG WHEN USER TRIES TO LEAVE WITHOUT SUBMITTING

    private void confirmBack() {
        if (confirmBack && verticalStepperForm.isAnyStepCompleted()) {
            BackConfirmationFragment backConfirmation = new BackConfirmationFragment();
            backConfirmation.setOnConfirmBack(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirmBack = true;
                }
            });
            backConfirmation.setOnNotConfirmBack(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirmBack = false;
                    finish();
                }
            });
            backConfirmation.show(getSupportFragmentManager(), null);
        } else {
            confirmBack = false;
            finish();
        }
    }*/
    }

    private void init_chips() {

        selectDrug = new ArrayList<>();
        for (int i = 0; i < DRUG_TOTAL; i++) {
            selectDrug.add("null");
            Log.e(TAG, "chipCheckedChange: " + selectDrug.get(i));
        }

        FlexboxLayout flexboxRapid = (FlexboxLayout) findViewById(R.id.flexbox_rapid);
        FlexboxLayout flexboxRapid2 = (FlexboxLayout) findViewById(R.id.flexbox_rapid2);
        FlexboxLayout flexboxNeutral = (FlexboxLayout) findViewById(R.id.flexbox_neutral);
        FlexboxLayout flexboxLongtime = (FlexboxLayout) findViewById(R.id.flexbox_longtime);
        FlexboxLayout flexboxMixed = (FlexboxLayout) findViewById(R.id.flexbox_mixed);

        ChipCloudConfig drawableConfig = new ChipCloudConfig()
                .selectMode(ChipCloud.SelectMode.multi)
                .checkedChipColor(Color.parseColor("#ddaa00"))
                .checkedTextColor(Color.parseColor("#ffffff"))
                .uncheckedChipColor(Color.parseColor("#e0e0e0"))
                .uncheckedTextColor(Color.parseColor("#000000"));

        final ChipCloud RapidChipCloud = new ChipCloud(this, flexboxRapid, drawableConfig);
        RapidChipCloud.addChip("휴마로그", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        RapidChipCloud.addChip("애피드라", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        RapidChipCloud.addChip("노보래피드", ContextCompat.getDrawable(this, R.drawable.fix_syringe));

        final ChipCloud Rapid2ChipCloud = new ChipCloud(this, flexboxRapid2, drawableConfig);
        Rapid2ChipCloud.addChip("휴물린R", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        Rapid2ChipCloud.addChip("노보린R", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        Rapid2ChipCloud.addChip("노보렛R", ContextCompat.getDrawable(this, R.drawable.fix_syringe));

        final ChipCloud NeutralChipCloud = new ChipCloud(this, flexboxNeutral, drawableConfig);
        NeutralChipCloud.addChip("인슈라타드", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        NeutralChipCloud.addChip("휴물린N", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        NeutralChipCloud.addChip("노보린N", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        NeutralChipCloud.addChip("노보렛N", ContextCompat.getDrawable(this, R.drawable.fix_syringe));

        final ChipCloud LongtimeChipCloud = new ChipCloud(this, flexboxLongtime, drawableConfig);
        LongtimeChipCloud.addChip("란투스", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        LongtimeChipCloud.addChip("레버미어", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        LongtimeChipCloud.addChip("투제오", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        LongtimeChipCloud.addChip("트레시바", ContextCompat.getDrawable(this, R.drawable.fix_syringe));

        final ChipCloud MixedChipCloud = new ChipCloud(this, flexboxMixed, drawableConfig);
        MixedChipCloud.addChip("노보믹스30", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        MixedChipCloud.addChip("노보믹스50", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        MixedChipCloud.addChip("노보믹스70", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        MixedChipCloud.addChip("믹스타드30", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        MixedChipCloud.addChip("휴마로그믹스25", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        MixedChipCloud.addChip("휴마로그믹스50", ContextCompat.getDrawable(this, R.drawable.fix_syringe));
        MixedChipCloud.addChip("휴물린70/30", ContextCompat.getDrawable(this, R.drawable.fix_syringe));

        RapidChipCloud.setListener(new ChipListener() {
            @Override
            public void chipCheckedChange(int index, boolean checked, boolean userClick) {
                if (userClick) {
                    String labelText = RapidChipCloud.getLabel(index);
                    if (checked) {
                        selectDrug.set(index, labelText);
                    } else {
                        selectDrug.set(index, "null");
                    }
                    Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s, label : %s", index, checked, RapidChipCloud.getLabel(index)));
                    for (int i = 0; i < selectDrug.size(); i++) {
                        Log.e(TAG, "chipCheckedChange: " + selectDrug.get(i));
                    }
                }
            }
        });

        Rapid2ChipCloud.setListener(new ChipListener() {
            @Override
            public void chipCheckedChange(int index, boolean checked, boolean userClick) {
                if (userClick) {
                    String labelText = Rapid2ChipCloud.getLabel(index);
                    Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s, label : %s", index, checked, Rapid2ChipCloud.getLabel(index)));
                    index += 3;
                    if (checked) {
                        selectDrug.set(index, labelText);
                    } else {
                        selectDrug.set(index, "null");
                    }

                    for (int i = 0; i < selectDrug.size(); i++) {
                        Log.e(TAG, "chipCheckedChange: " + selectDrug.get(i));
                    }
                }
            }
        });

        NeutralChipCloud.setListener(new ChipListener() {
            @Override
            public void chipCheckedChange(int index, boolean checked, boolean userClick) {
                if (userClick) {
                    String labelText = NeutralChipCloud.getLabel(index);
                    Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s, label : %s", index, checked, NeutralChipCloud.getLabel(index)));
                    index += 6;
                    if (checked) {
                        selectDrug.set(index, labelText);
                    } else {
                        selectDrug.set(index, "null");
                    }

                    for (int i = 0; i < selectDrug.size(); i++) {
                        Log.e(TAG, "chipCheckedChange: " + selectDrug.get(i));
                    }
                }
            }
        });

        LongtimeChipCloud.setListener(new ChipListener() {
            @Override
            public void chipCheckedChange(int index, boolean checked, boolean userClick) {
                if (userClick) {
                    String labelText = LongtimeChipCloud.getLabel(index);
                    Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s, label : %s", index, checked, LongtimeChipCloud.getLabel(index)));
                    index += 10;
                    if (checked) {
                        selectDrug.set(index, labelText);
                    } else {
                        selectDrug.set(index, "null");
                    }

                    for (int i = 0; i < selectDrug.size(); i++) {
                        Log.e(TAG, "chipCheckedChange: " + selectDrug.get(i));
                    }
                }
            }
        });
        MixedChipCloud.setListener(new ChipListener() {
            @Override
            public void chipCheckedChange(int index, boolean checked, boolean userClick) {
                if (userClick) {
                    String labelText = MixedChipCloud.getLabel(index);
                    Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s, label : %s", index, checked, MixedChipCloud.getLabel(index)));
                    index += 14;
                    if (checked) {
                        selectDrug.set(index, labelText);
                    } else {
                        selectDrug.set(index, "null");
                    }

                    for (int i = 0; i < selectDrug.size(); i++) {
                        Log.e(TAG, "chipCheckedChange: " + selectDrug.get(i));
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new PanterDialog(this)
                .setHeaderBackground(R.drawable.pattern_bg_orange)
                .setTitle("알림")
                .setPositive("계속할래요") // You can pass also View.OnClickListener as second param
                .setNegative("그만둘래요", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setMessage("거의다 왔는데 그만두시겠어요?")
                .isCancelable(false)
                .show();
    }
}
