package com.dreamwalker.knu2018.dteacher.WearableSync;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.R;
import java.util.ArrayList;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WearableRealTimeActivity extends BleRealTimeLibrary {

    private static final String TAG = "WearableRealTimeActivit";

    @BindView(R.id.buttonScan)
    Button buttonScan;
    @BindView(R.id.buttonSerialSend)
    Button buttonClear;

    @BindView(R.id.serialReveicedText)
    TextView serialReceivedText;

    // TODO: 2018-01-25 Library Service에서 받은 데이터를 처리하기위한 변수
    ArrayList<String> arrayList = new ArrayList<>();
    String[] tmpString;
    int tmpIntEnd;
    int tmpIntStart;
    float tmpResult;
    String percent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearable_real_time);
        ButterKnife.bind(this);
        onCreateProcess();                                                        //onCreate Process by BlunoLibrary

        //serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

        //serialReceivedText = (TextView) findViewById(R.id.serialReveicedText);    //initial the EditText of the received data
//        serialSendText = (EditText) findViewById(R.id.serialSendText);            //initial the EditText of the sending data
//        buttonSerialSend = (Button) findViewById(R.id.buttonSerialSend);        //initial the button for sending the data
        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                serialReceivedText.setText("");
                //serialSend(serialSendText.getText().toString());                //send the data to the BLUNO
            }
        });

        buttonScan = (Button) findViewById(R.id.buttonScan);                    //initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                buttonScanOnClickProcess();                                        //Alert Dialog for selecting the BLE device
            }
        });

//        buttonClear = (Button) findViewById(R.id.buttonClear);
//        buttonClear.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                serialReceivedText.setText("");
//            }
//        });

    }

    protected void onResume() {
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();                                                        //onResume Process by BlunoLibrary
        // TODO: 2018-01-26 액티비티가 다시 실행되면 0으로 초기화
        arrayList.clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);                    //onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();                                                        //onPause Process by BlunoLibrary
    }

    protected void onStop() {
        super.onStop();
        onStopProcess();                                                        //onStop Process by BlunoLibrary
        arrayList.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();                                                        //onDestroy Process by BlunoLibrary
    }

    @Override
    public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
        switch (theConnectionState) {                                            //Four connection state
            case isConnected:
                buttonScan.setText("Connected");
                break;
            case isConnecting:
                buttonScan.setText("Connecting");
                break;
            case isToScan:
                buttonScan.setText("Scan");
                break;
            case isScanning:
                buttonScan.setText("Scanning");
                break;
            case isDisconnecting:
                buttonScan.setText("isDisconnecting");
                break;
            default:
                break;
        }
    }

    @Override
    public void onSerialTrans(String theString) {
        //System.out.println("realData : " + theString);
    }

    // TODO: 2018-02-20 부모 클래스에 선언한 추상 클래스
    @Override
    public void onSerialReceived(String theString) {                            //Once connection data received, this function will be called
        // TODO Auto-generated method stub
        //Intent intent = new Intent(this, SyncWearableResultActivity.class);

        if (theString == null) {
            serialReceivedText.append("");
        } else {
            serialReceivedText.append(theString + "\n");
        }
        //append the text into the EditText
        //The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
        //((ScrollView) serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);

    }



}
