package com.dreamwalker.knu2018.dteacher.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.Model.RealTime;
import com.dreamwalker.knu2018.dteacher.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class GraphAnalysisActivity extends AppCompatActivity {

    private static final String TAG = "GraphAnalysisActivity";

    @BindView(R.id.detail_linechart)
    LineChart lineChart;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.coordinator)
    RelativeLayout coordinator;

    LineDataSet lineDataSet;
    LineData lineData;

    ArrayList<Entry> yValue;
    ArrayList<String> monitoringDataList;
    ArrayList<ILineDataSet> dataSets;
    ArrayList<RealTime> realTimeArrayList;
    private int pageNumber;

    //Bottom Sheet
    private SweetSheet mSweetSheet;
    Workbook workbook;
    Sheet sheet; // 새로운 시트 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_analysis);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Detail Graph");
        ButterKnife.bind(this);

        Paper.init(this);

        yValue = new ArrayList<>();
        monitoringDataList = new ArrayList<>();
        realTimeArrayList = new ArrayList<>();

        pageNumber = getIntent().getIntExtra("page", 0);
        Log.e(TAG, "pageNumber: " + pageNumber);

        dataProcessing();
        setupRecyclerView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs Save External File Access");
                //builder.setMessage("Please grant location access so this app can detect beacons & receive monitoring data.");
                builder.setMessage("데이터를 저장하기 위해서는 권한이 필요합니다.");
                builder.setPositiveButton("Ok", null);
                builder.setOnDismissListener(dialog -> requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100));
                builder.show();
            }
        }
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        mSweetSheet.toggle();
    }

    private void dataProcessing() {
        XAxis xAxis;
        switch (pageNumber) {
            case 0:

                monitoringDataList = getIntent().getStringArrayListExtra("realdata");

                for (int i = 0; i < monitoringDataList.size(); i++) {
                    Log.e(TAG, i + " " + monitoringDataList.get(i));
                    float hr = Float.parseFloat(monitoringDataList.get(i));
                    yValue.add(new Entry(i, hr));
                }

                xAxis = lineChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                lineDataSet = new LineDataSet(yValue, "전체 데이터");
                lineDataSet.setColor(Color.RED);
                lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                lineDataSet.setCubicIntensity(0.1f);
                dataSets = new ArrayList<>();
                dataSets.add(lineDataSet);
                lineData = new LineData(dataSets);

                lineChart.setData(lineData);
                lineChart.animateX(1000);
                lineChart.getAxisRight().setEnabled(false);
                break;
            case 10:
                realTimeArrayList = Paper.book("filePicked").read("data");
                for (int i = 0; i < realTimeArrayList.size(); i++) {
                    Log.e(TAG, i + " " + realTimeArrayList.get(i));
                    float hr = Float.parseFloat(realTimeArrayList.get(i).getValue());
                    yValue.add(new Entry(i, hr));
                }

                xAxis = lineChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                lineDataSet = new LineDataSet(yValue, "전체 데이터");
                lineDataSet.setColor(Color.RED);
                dataSets = new ArrayList<>();
                dataSets.add(lineDataSet);
                lineData = new LineData(dataSets);

                lineChart.setData(lineData);
                lineChart.animateX(1000);
                lineChart.getAxisRight().setEnabled(false);
                break;
            default:
                break;
        }

    }

    /**
     * 리사이클러 Bottom Sheet 생성
     * 공유하기
     * 저장하기
     * 등 그래프를 보고 해야할 일을 처리해야함.
     *
     * @author : JAICHANGPARK(DREAMWALKER)
     */
    private void setupRecyclerView() {

        ArrayList<MenuEntity> list = new ArrayList<>();
        //添加假数据
        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.drawable.ic_save_black_24dp;
        menuEntity1.title = "SAVE";
        MenuEntity menuEntity2 = new MenuEntity();
        menuEntity2.iconId = R.drawable.ic_list_black_24dp;
        menuEntity2.title = "LIST";
        MenuEntity menuEntity3 = new MenuEntity();
        menuEntity3.iconId = R.drawable.ic_menu_share;
        menuEntity3.title = "SHARE";
        switch (pageNumber) {
            case 0:
                list.add(menuEntity1);
                list.add(menuEntity2);
                list.add(menuEntity3);
                break;
            case 10:
                list.add(menuEntity3);
                break;
            default:
                break;
        }

        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet = new SweetSheet(coordinator);
        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
        mSweetSheet.setMenuList(list);
        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
        //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
        mSweetSheet.setBackgroundEffect(new BlurEffect(14));


        //设置点击事件
        mSweetSheet.setOnMenuItemClickListener((position, menuEntity11) -> {
            //即时改变当前项的颜色
//                list.get(position).titleColor = 0xff5823ff;
//                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();
            //根据返回值, true 会关闭 SweetSheet ,false 则不会.
            if (pageNumber == 0){
                switch (position) {
                    case 0:
                        saveExcel();
                        break;
                    case 1:
                        startActivity(new Intent(GraphAnalysisActivity.this, DetailListActivity.class));
                }
            }else if (pageNumber == 10){
                switch (position) {
                    case 0:
                        String filePath = getIntent().getStringExtra("filepath");
                        // TODO: 2018-02-22 SyncWearable -> filePickedAtivity -> this 
                        String fileName = getIntent().getStringExtra("filename");
                        File xlsFile = new File(getExternalFilesDir(null), fileName);
                        Uri path = Uri.fromFile(xlsFile);
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("application/excel");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, path);
                        startActivity(Intent.createChooser(shareIntent, "로그파일 공유하기"));
                        //Snackbar.make(getWindow().getDecorView().getRootView(),"공유하기",Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }

            //Toast.makeText(GraphAnalysisActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    private void saveExcel() {

        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        String file_name = "log" + "_" + datetime + ".xls";

        if (Paper.book("realtime").read("data") != null) {
            realTimeArrayList = Paper.book("realtime").read("logging");
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), "저장된 데이터가 없습니다.", Snackbar.LENGTH_SHORT).show();
        }
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("LogSheet"); // 새로운 시트 생성
        Row row = sheet.createRow(0); // 새로운 행 생성

        Cell cell;

        cell = row.createCell(0); // 1번 셀 생성
        cell.setCellValue("Value"); // 1번 셀 값 입력

        cell = row.createCell(1); // 2번 셀 생성
        cell.setCellValue("DateTime"); // 2번 셀 값 입

        for (int i = 0; i < realTimeArrayList.size(); i++) { // 데이터 엑셀에 입력
            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(realTimeArrayList.get(i).getValue());
            cell = row.createCell(1);
            cell.setCellValue(realTimeArrayList.get(i).getDatetime());
        }

        File xlsFile = new File(getExternalFilesDir(null), file_name);

        try {
            FileOutputStream os = new FileOutputStream(xlsFile);
            workbook.write(os); // 외부 저장소에 엑셀 파일 생성
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), xlsFile.getAbsolutePath() + "에 저장되었습니다", Toast.LENGTH_SHORT).show();

        Uri path = Uri.fromFile(xlsFile);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/excel");
        shareIntent.putExtra(Intent.EXTRA_STREAM, path);
        startActivity(Intent.createChooser(shareIntent, "엑셀 내보내기"));
    }

    @Override
    public void onBackPressed() {
        if (mSweetSheet.isShow()) {
            mSweetSheet.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    // TODO: 2018-02-21 외부 파일 저장 권한 확인
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 100: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("permission", "WRITE_EXTERNAL_STORAGE permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since WRITE_EXTERNAL_STORAGE has not been granted, " +
                            "this app will not be able to save logging data when in the background and receive data. ");
                    builder.setPositiveButton("OK", null);
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
