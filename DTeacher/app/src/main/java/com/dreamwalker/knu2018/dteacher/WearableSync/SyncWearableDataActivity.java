package com.dreamwalker.knu2018.dteacher.WearableSync;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.R;

import java.util.ArrayList;

import me.itangqi.waveloadingview.WaveLoadingView;


public class SyncWearableDataActivity extends BlunoLibrary {
    private static final String TAG = "SyncWearableDataActivit";
    private Button buttonScan;
    TextView serialReceivedText;
    WaveLoadingView waveLoadingView;
    String tmpYear = "";
    int intYear = 0;

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
        setContentView(R.layout.activity_sync_wearable_data);
        onCreateProcess();                                                        //onCreate Process by BlunoLibrary

        waveLoadingView = (WaveLoadingView)findViewById(R.id.waveLoadingView);
        waveLoadingView.setProgressValue(0);
        waveLoadingView.setBottomTitle("");
        waveLoadingView.setTopTitle("");
        waveLoadingView.setCenterTitle("Please Search Device");

        //serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

        //serialReceivedText = (TextView) findViewById(R.id.serialReveicedText);    //initial the EditText of the received data
//        serialSendText = (EditText) findViewById(R.id.serialSendText);            //initial the EditText of the sending data
//        buttonSerialSend = (Button) findViewById(R.id.buttonSerialSend);        //initial the button for sending the data
//        buttonSerialSend.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                serialSend(serialSendText.getText().toString());                //send the data to the BLUNO
//            }
//        });

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


        // TODO: 2017-11-17 BLE 관련 Permission 주기
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                //builder.setMessage("Please grant location access so this app can detect beacons & receive monitoring data.");
                builder.setMessage("장비와 데이터를 동기화하기위해서는 권한 허가가 필요합니다.");
                builder.setPositiveButton("Ok", null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                    }
                });
                builder.show();
            }
        }
    }

    protected void onResume() {
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();                                                        //onResume Process by BlunoLibrary
        // TODO: 2018-01-26 액티비티가 다시 실행되면 0으로 초기화
        waveLoadingView.setProgressValue(0);
        waveLoadingView.setCenterTitle("");
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
                waveLoadingView.setCenterTitle("Connection Waiting");
                break;
            case isToScan:
                buttonScan.setText("Scan");
                waveLoadingView.setCenterTitle("Please Connect Device");
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

    // TODO: 2018-01-25 추상 클래스로 라이브러리에서 호출되어 사용되는 함수.  데이터 표기 및 처리를 이곳에서 진행하면 된다.
    @Override
    public void onSerialReceived(String theString) {                            //Once connection data received, this function will be called
        // TODO Auto-generated method stub
        Intent intent = new Intent(this, SyncWearableResultActivity.class);

        if (theString == null) {
            serialReceivedText.append("");
        } else {
            tmpString = theString.split(",");
            tmpIntStart = Integer.parseInt(tmpString[1]);
            tmpIntEnd = Integer.parseInt(tmpString[2]);
            Log.e(TAG,"MainActivity tmpIntStart :  " + tmpIntStart);
            Log.e(TAG,"MainActivity tmpIntEnd :  " + tmpIntEnd);

            if (tmpIntEnd != 0) {

                Log.e(TAG,"MainActivity tmpInt :  " + tmpIntStart / tmpIntEnd);
                // TODO: 2018-01-26 전송정도를 계산하는 코드
                tmpResult = ((float)(tmpIntStart) / tmpIntEnd) * 100;
                percent = String.valueOf(tmpResult) + "% \n";

                // TODO: 2018-01-26 wave 프로그래스 값 처리하는 부분
                waveLoadingView.setProgressValue((int)tmpResult);
                waveLoadingView.setCenterTitle(percent);
                // TODO: 2018-01-26 ArrayList 처리하는 부분.
                arrayList.add(theString);

                //serialReceivedText.append(theString);
                //serialReceivedText.append(percent);

                if (arrayList.size() == tmpIntEnd + 1) {
                    intent.putExtra("data", arrayList);
                    startActivity(intent);
                }

            }else {
                waveLoadingView.setCenterTitle("Connection Waiting");
            }
            Log.e(TAG,"MainActivity arrayList size :  " + arrayList.size());
        }
        if (theString.equals("102")) {
            intent.putExtra("data", arrayList);
            startActivity(intent);
            finish();
        }
        //append the text into the EditText
        //The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
        //((ScrollView) serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);

    }

    // TODO: 2017-11-17 마쉬멜로우 이상부터 권한 설정이 꼭 필요하다.
    // TODO: 2017-11-17 권한이 설정되지 않으면 블루투스 스캔이 되지 않는다. 그래서 사용자의 권한을 꼭 얻어야 한다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("permission", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, " +
                            "this app will not be able to discover beacons when in the background and receive data. ");
                    builder.setPositiveButton("Ok", null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();


                }
                return;
            }
        }
    }

}
