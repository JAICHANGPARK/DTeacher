package com.dreamwalker.knu2018.dteacher.SignUpActivity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.R
import com.eminayar.panter.PanterDialog
import com.google.android.flexbox.FlexboxLayout
import com.shuhart.stepview.StepView

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import fisk.chipcloud.ChipCloud
import fisk.chipcloud.ChipCloudConfig
import fisk.chipcloud.ChipListener
import info.hoang8f.widget.FButton

class SignUpActivity6 : AppCompatActivity() {

    @BindView(R.id.step_view)
    internal var setpview: StepView? = null
    @BindView(R.id.nextButton)
    internal var nextButton: FButton? = null

    internal var selectDrug: MutableList<String>
    internal var userSignUpInfo: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Sign Up"
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_sign_up6)

        ButterKnife.bind(this)

        userSignUpInfo = ArrayList()
        userSignUpInfo = intent.getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_5)

        init_chips()

        val mySteps = arrayOf("Name", "Email", "Phone Number", "Gender")

        val colorPrimary = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
        val colorPrimaryDark = ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark)

        setpview!!.state
                .selectedTextColor(ContextCompat.getColor(this, R.color.fbutton_color_midnight_blue))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(this, R.color.fbutton_color_sun_flower))
                .selectedCircleRadius(resources.getDimensionPixelSize(R.dimen.dp14))
                .selectedStepNumberColor(ContextCompat.getColor(this, R.color.fbutton_color_midnight_blue))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(object : ArrayList<String>() {
                    init {
                        add("필수 정보")
                        add("기본 정보")
                        add("당뇨 정보")
                        add("투약 정보")
                    }
                })
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(4)
                .animationDuration(resources.getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(resources.getDimensionPixelSize(R.dimen.dp1))
                .textSize(resources.getDimensionPixelSize(R.dimen.sp14))
                .stepNumberTextSize(resources.getDimensionPixelSize(R.dimen.sp16))
                // other state methods are equal to the corresponding xml attributes
                .commit()

        setpview!!.go(3, false)

        nextButton!!.setOnClickListener {
            val stringBuilder = StringBuilder()
            // TODO: 2018-02-03 내가 짜도 참 괜찮은 코드
            for (i in selectDrug.indices) {
                if (!selectDrug[i].contains("null")) {
                    stringBuilder.append(selectDrug[i])
                    stringBuilder.append(",")
                }
            }

            userSignUpInfo.add(stringBuilder.toString())
            Toast.makeText(this@SignUpActivity6, "Result " + stringBuilder.toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, SignUpDoneActivity::class.java)
            intent.putStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_6, userSignUpInfo)
            intent.putExtra(IntentConst.SIGNUP_REGISTER_TYPE, 1)
            startActivity(intent)
            finish()
        }

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

    private fun init_chips() {

        selectDrug = ArrayList()
        for (i in 0 until DRUG_TOTAL) {
            selectDrug.add("null")
            Log.e(TAG, "chipCheckedChange: " + selectDrug[i])
        }

        val flexboxRapid = findViewById<View>(R.id.flexbox_rapid) as FlexboxLayout
        val flexboxRapid2 = findViewById<View>(R.id.flexbox_rapid2) as FlexboxLayout
        val flexboxNeutral = findViewById<View>(R.id.flexbox_neutral) as FlexboxLayout
        val flexboxLongtime = findViewById<View>(R.id.flexbox_longtime) as FlexboxLayout
        val flexboxMixed = findViewById<View>(R.id.flexbox_mixed) as FlexboxLayout

        val drawableConfig = ChipCloudConfig()
                .selectMode(ChipCloud.SelectMode.multi)
                .checkedChipColor(Color.parseColor("#ddaa00"))
                .checkedTextColor(Color.parseColor("#ffffff"))
                .uncheckedChipColor(Color.parseColor("#e0e0e0"))
                .uncheckedTextColor(Color.parseColor("#000000"))

        val RapidChipCloud = ChipCloud(this, flexboxRapid, drawableConfig)
        RapidChipCloud.addChip("휴마로그", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        RapidChipCloud.addChip("애피드라", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        RapidChipCloud.addChip("노보래피드", ContextCompat.getDrawable(this, R.drawable.fix_syringe))

        val Rapid2ChipCloud = ChipCloud(this, flexboxRapid2, drawableConfig)
        Rapid2ChipCloud.addChip("휴물린R", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        Rapid2ChipCloud.addChip("노보린R", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        Rapid2ChipCloud.addChip("노보렛R", ContextCompat.getDrawable(this, R.drawable.fix_syringe))

        val NeutralChipCloud = ChipCloud(this, flexboxNeutral, drawableConfig)
        NeutralChipCloud.addChip("인슈라타드", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        NeutralChipCloud.addChip("휴물린N", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        NeutralChipCloud.addChip("노보린N", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        NeutralChipCloud.addChip("노보렛N", ContextCompat.getDrawable(this, R.drawable.fix_syringe))

        val LongtimeChipCloud = ChipCloud(this, flexboxLongtime, drawableConfig)
        LongtimeChipCloud.addChip("란투스", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        LongtimeChipCloud.addChip("레버미어", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        LongtimeChipCloud.addChip("투제오", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        LongtimeChipCloud.addChip("트레시바", ContextCompat.getDrawable(this, R.drawable.fix_syringe))

        val MixedChipCloud = ChipCloud(this, flexboxMixed, drawableConfig)
        MixedChipCloud.addChip("노보믹스30", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        MixedChipCloud.addChip("노보믹스50", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        MixedChipCloud.addChip("노보믹스70", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        MixedChipCloud.addChip("믹스타드30", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        MixedChipCloud.addChip("휴마로그믹스25", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        MixedChipCloud.addChip("휴마로그믹스50", ContextCompat.getDrawable(this, R.drawable.fix_syringe))
        MixedChipCloud.addChip("휴물린70/30", ContextCompat.getDrawable(this, R.drawable.fix_syringe))

        RapidChipCloud.setListener { index, checked, userClick ->
            if (userClick) {
                val labelText = RapidChipCloud.getLabel(index)
                if (checked) {
                    selectDrug[index] = labelText
                } else {
                    selectDrug[index] = "null"
                }
                Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s, label : %s", index, checked, RapidChipCloud.getLabel(index)))
                for (i in selectDrug.indices) {
                    Log.e(TAG, "chipCheckedChange: " + selectDrug[i])
                }
            }
        }

        Rapid2ChipCloud.setListener { index, checked, userClick ->
            var index = index
            if (userClick) {
                val labelText = Rapid2ChipCloud.getLabel(index)
                Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s, label : %s", index, checked, Rapid2ChipCloud.getLabel(index)))
                index += 3
                if (checked) {
                    selectDrug[index] = labelText
                } else {
                    selectDrug[index] = "null"
                }

                for (i in selectDrug.indices) {
                    Log.e(TAG, "chipCheckedChange: " + selectDrug[i])
                }
            }
        }

        NeutralChipCloud.setListener { index, checked, userClick ->
            var index = index
            if (userClick) {
                val labelText = NeutralChipCloud.getLabel(index)
                Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s, label : %s", index, checked, NeutralChipCloud.getLabel(index)))
                index += 6
                if (checked) {
                    selectDrug[index] = labelText
                } else {
                    selectDrug[index] = "null"
                }

                for (i in selectDrug.indices) {
                    Log.e(TAG, "chipCheckedChange: " + selectDrug[i])
                }
            }
        }

        LongtimeChipCloud.setListener { index, checked, userClick ->
            var index = index
            if (userClick) {
                val labelText = LongtimeChipCloud.getLabel(index)
                Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s, label : %s", index, checked, LongtimeChipCloud.getLabel(index)))
                index += 10
                if (checked) {
                    selectDrug[index] = labelText
                } else {
                    selectDrug[index] = "null"
                }

                for (i in selectDrug.indices) {
                    Log.e(TAG, "chipCheckedChange: " + selectDrug[i])
                }
            }
        }
        MixedChipCloud.setListener { index, checked, userClick ->
            var index = index
            if (userClick) {
                val labelText = MixedChipCloud.getLabel(index)
                Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s, label : %s", index, checked, MixedChipCloud.getLabel(index)))
                index += 14
                if (checked) {
                    selectDrug[index] = labelText
                } else {
                    selectDrug[index] = "null"
                }

                for (i in selectDrug.indices) {
                    Log.e(TAG, "chipCheckedChange: " + selectDrug[i])
                }
            }
        }
    }

    override fun onBackPressed() {
        PanterDialog(this)
                .setHeaderBackground(R.drawable.pattern_bg_orange)
                .setTitle("알림")
                .setPositive("계속할래요") // You can pass also View.OnClickListener as second param
                .setNegative("그만둘래요") { finish() }
                .setMessage("거의다 왔는데 그만두시겠어요?")
                .isCancelable(false)
                .show()
    }

    companion object {
        private val TAG = "SignUpActivity6"
        private val DRUG_TOTAL = 25
    }
}
