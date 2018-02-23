package com.dreamwalker.knu2018.dteacher.WearableSync;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.Activity.GraphAnalysisActivity;
import com.dreamwalker.knu2018.dteacher.Model.RealTime;
import com.dreamwalker.knu2018.dteacher.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class WearableRealTimeActivity extends BleRealTimeLibrary {

    private static final String TAG = "WearableRealTimeActivit";

    @BindView(R.id.buttonScan)
    Button buttonScan;
    @BindView(R.id.buttonSerialSend)
    Button buttonClear;
    @BindView(R.id.buttonSave)
    Button buttonSave;
    @BindView(R.id.buttonGraph)
    Button buttonGraph;

//    @BindView(R.id.serialReveicedText)
//    TextView serialReceivedText;

    @BindView(R.id.line_chart)
    LineChart lineChart;

    private LineDataSet lineDataSet;
    private LineData lineData;
    private ArrayList<Entry> realtimeData;
    ArrayList<RealTime> loggingArray;
    ArrayList<String> tempArray;

    private int cnt = 0;
    // TODO: 2018-01-25 Library Service에서 받은 데이터를 처리하기위한 변수
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearable_real_time);
        ButterKnife.bind(this);


        onCreateProcess();

        // TODO: 2018-02-21 임시 저장할 NoSQL 객체  
        Paper.init(this);

        realtimeData = new ArrayList<>();
        tempArray = new ArrayList<>();
        loggingArray = new ArrayList<>();

        // TODO: 2018-02-21 차트 속성 설정.
        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        buttonClear.setOnClickListener(v-> Log.e(TAG, "onCreate: 람다 클릭 "));
        buttonScan.setOnClickListener(v->buttonScanOnClickProcess());

    }

    @OnClick(R.id.buttonSave)
    public void onButtonSaveClicked() {

        onStopProcess();
        onPauseProcess();

        if (realtimeData.size() == 0) { //실시간 데이터가 없으면
            Toast.makeText(WearableRealTimeActivity.this, "저장할 데이터가 없어요 ㅠㅠ", Toast.LENGTH_SHORT).show();
        } else { // 축적된 실시간 데이터가 있다면
            if (Paper.book("realtime").read("data") == null) { // 만약 sql에 데이터가 없다면
                Paper.book("realtime").write("data", realtimeData); // 데이터를 저장한다.
                Paper.book("realtime").write("logging", loggingArray);

                Toast.makeText(WearableRealTimeActivity.this, "데이터가 없어 저장했어요", Toast.LENGTH_SHORT).show();
                buttonSave.setText("저장완료:)");
                buttonSave.setEnabled(false);
                buttonGraph.setVisibility(View.VISIBLE);

            } else { // 그렇지 않고 데이터가 있다면
                Paper.book("realtime").delete("data"); //세로운 데이터를 저장하기 위해 기존 것을 다 지운다.
                Paper.book("realtime").write("data", realtimeData); // 세로운 데이터를 다시 넣는다.
                Paper.book("realtime").delete("logging");
                Paper.book("realtime").write("logging", loggingArray);
                Toast.makeText(WearableRealTimeActivity.this, "기존 데이터를 지우고 다시 저장했어요", Toast.LENGTH_SHORT).show();
                buttonSave.setText("저장완료:)");
                buttonSave.setEnabled(false);
                buttonGraph.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.buttonGraph)
    public void onButtonGraphClicked() {
        if (realtimeData.size() == 0) {
            Toast.makeText(WearableRealTimeActivity.this, "데이터가 없네요?", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < realtimeData.size(); i++) {
                float st = realtimeData.get(i).getY();
                String stt = String.valueOf(st);
                tempArray.add(stt);
                Log.e(TAG, "tempArray: " + tempArray.get(i));
            }
            Intent intent = new Intent(getBaseContext(), GraphAnalysisActivity.class);
            intent.putExtra("realdata", tempArray);
            intent.putExtra("page", 0);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);                    //onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
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

                buttonSave.setText("저장하기");
                buttonSave.setEnabled(true);
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
        String receiveValue;
        float floatValue;
        //Intent intent = new Intent(this, SyncWearableResultActivity.class);
        if (theString != null) {
            receiveValue = theString;
            //serialReceivedText.append("");
        } else {
            receiveValue = "";
            //serialReceivedText.append(theString + "\n");
        }
        floatValue = Float.parseFloat(receiveValue);
        // TODO: 2018-02-21 호출될때 마다 시간 갱신
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        // TODO: 2018-02-21 line데이터 그리기 위한 변수  
        realtimeData.add(new Entry(cnt, floatValue));
        // TODO: 2018-02-21 데이터 로깅용 리스트
        loggingArray.add(new RealTime(String.valueOf(floatValue), datetime));
        Log.e(TAG, "realtimeData: " + realtimeData.get(cnt));
        Log.e(TAG, "loggingArray: " + loggingArray.get(cnt).getDatetime() + ", " + loggingArray.get(cnt).getValue());
        lineDataSet = new LineDataSet(realtimeData, "실시간 데이터");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setColor(Color.RED);
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        // TODO: 2017-11-17 실시간 그래프를 그리는 핵심 코드.
        lineChart.moveViewToX(lineData.getEntryCount());
        lineChart.notifyDataSetChanged();
        ++cnt;
    }

    protected void onResume() {
        super.onResume();
        Log.e(TAG, "RealtimeActivity onResume");
        onResumeProcess();                                                        //onResume Process by BlunoLibrary
        // TODO: 2018-01-26 액티비티가 다시 실행되면 0으로 초기화
        //arrayList.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "RealtimeActivity onPause");
        //onPauseProcess();                                                        //onPause Process by BlunoLibrary
    }

    protected void onStop() {
        super.onStop();
        Log.e(TAG, "RealtimeActivity onStop");
        //onStopProcess();                                                        //onStop Process by BlunoLibrary
        //arrayList.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();                                                        //onDestroy Process by BlunoLibrary
    }

    // TODO: 2018-02-23 두번 눌러 종료하기
    private long lastTimeBackPressed;
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        Snackbar.make(getWindow().getDecorView().getRootView(), "'뒤로' 버튼을 한번 더 눌러 종료합니다.", Snackbar.LENGTH_SHORT).show();
        //Toast.makeText(this, "'뒤로' 버튼을 한번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}
