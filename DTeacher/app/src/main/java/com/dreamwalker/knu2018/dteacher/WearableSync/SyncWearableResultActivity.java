package com.dreamwalker.knu2018.dteacher.WearableSync;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.dreamwalker.knu2018.dteacher.Adapter.WearableSyncAdapter;
import com.dreamwalker.knu2018.dteacher.Model.Pattern;
import com.dreamwalker.knu2018.dteacher.R;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SyncWearableResultActivity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";
    ArrayList<String> receiveList;
    ArrayList<Pattern> patternList;
    TextView patternText, diabetesText;
    String resultString;
    String[] data ;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    WearableSyncAdapter adapter;
    List<Pattern> patternAdapterList;

    int pCount = 0; // 행동 패턴 데이터 개수
    int pnCount = 0; // 행동 패턴 일반 데이터 개수
    int pwCount = 0; // 행동 패턴 운동 데이터 개수
    int pfCounf = 0; // 행동 패턴 음식 데이터 개수
    int dCount = 0; // 당뇨 데이터 개수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_wearable_result);

        init();

        // TODO: 2018-01-26 MainActivity에서 보낸 데이터를 여기서 받아 처리한다.
        receiveList = getIntent().getStringArrayListExtra("data");

        Log.e(TAG, "onCreate: " + receiveList.size() );

        for (int i = 0; i < receiveList.size(); i++){
            resultString += receiveList.get(i);
            resultString += "\n\n";
            Log.e(TAG, "onCreate: " + receiveList.get(i) );
        }

        // TODO: 2018-01-26 데이터 처리를 어떻게 할 것 인지
        // TODO: 2018-01-26 받아온 데이터를 패턴 모델 제네릭을 가지는 리스트에 복사하는 과정

        long start_time = System.currentTimeMillis(); // 데이터 처리과정 시간 측정 시작

        for (int i = 0; i < receiveList.size(); i++){
            data = receiveList.get(i).split(",");
            //Log.e(TAG, "onCreate: " + data[i] );
            // TODO: 2018-01-26 Model data V1.0
            //patternList.add(new Pattern(data[4],data[5],data[6],data[7],data[8],data[9],data[10],data[11]));
            // TODO: 2018-01-26 Model data v1.1
            patternList.add(new Pattern(data[0],data[4],data[5],data[6],data[7],data[8],data[9],data[10],data[11]));

            // TODO: 2018-01-26 들어온 데이터의 개수를 구분하는 코드  카운트변수는 맴버 변수로 되어있음.
            if (data[0].equals("16")){
                pCount++;
                Paper.book("pattern").write("sync", patternList.get(i));
            }else if (data[0].equals("32")){
                dCount++;
                Paper.book("diabetes").write("sync", patternList.get(i));
            }
        }
        long end_time = System.currentTimeMillis();  // 데이터 처리과정 시간 측정 종료

        Log.e(TAG, "data processing Time: " + (end_time - start_time) + " ms" );

        for (int i = 0; i < patternList.size(); i++){
            Log.e(TAG, "patternList: " + patternList.get(i) );
        }

        // TODO: 2018-01-26 전달 받은 list에서 인덱스 0 삭제 : 시작 신호 세그먼트
        patternList.remove(0);
        // TODO: 2018-01-26 전달 받은 데이터 개수를 표기
        patternText.setText(String.valueOf(pCount) + " 개");
        diabetesText.setText(String.valueOf(dCount) + " 개");

        // TODO: 2018-01-26 RecyclerView Adapter 설정하는 곳 .
        recyclerView.setAdapter(adapter);
        //textView.append(resultString);

        // TODO: 2018-01-26 그래프 그리는 기능 추가해야 함.
        // TODO: 2018-01-26 데이터 저장 기능 추가해야 함. DB에 어떻게 저장할 건지

    }

    public void init(){

        Paper.init(this);

        patternAdapterList = new ArrayList<>();
        receiveList = new ArrayList<>();
        patternList = new ArrayList<>();

        patternText = (TextView)findViewById(R.id.patterNumText);
        diabetesText = (TextView)findViewById(R.id.diabetesNumText);
        recyclerView = (RecyclerView)findViewById(R.id.dataRecyclerView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WearableSyncAdapter(this, patternList);
    }
}
