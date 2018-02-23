package com.dreamwalker.knu2018.dteacher.Activity;

import android.animation.PropertyValuesHolder;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import com.db.chart.animation.Animation;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;
import com.dreamwalker.knu2018.dteacher.Adapter.DetailListAdapter;
import com.dreamwalker.knu2018.dteacher.Model.RealTime;
import com.dreamwalker.knu2018.dteacher.R;
import com.jonas.jgraph.graph.JcoolGraph;
import com.jonas.jgraph.models.Jchart;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

import static com.jonas.jgraph.inter.BaseGraph.SELECETD_MSG_SHOW_TOP;

public class FilePickedActivity extends AppCompatActivity {

    private static final String TAG = "FilePickedActivity";
    String filePath;

    @BindView(R.id.filepick_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

//    @BindView(R.id.sug_recode_line)
//    JcoolGraph mLineChar;

    @BindView(R.id.chart)
    LineChartView mChart;

    DetailListAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<RealTime> readFileList;
    List<Jchart> lines = new ArrayList<>();
    List<Jchart> graphLine = new ArrayList<>();
    private int chartNum = 14;

    String[] mLabels = null;
    float[] mValues = null;
    float[] mLableValues = null;

    private Tooltip mTip;

    float minYLabel;
    float maxYLabel;

    String filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picked);
        ButterKnife.bind(this);
        Paper.init(this);
        readFileList = new ArrayList<>();

        filename = getIntent().getStringExtra("filename");

        mTip = new Tooltip(this, R.layout.linechart_three_tooltip, R.id.value);

        mTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        mTip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            mTip.setPivotX(Tools.fromDpToPx(65) / 2);
            mTip.setPivotY(Tools.fromDpToPx(25));
        }

        // TODO: 2018-02-22 파일의 행 개수를 가져오는 곳 . 이곳에서 그래프의 리드 개수를 파악한다.
        long startTime = System.currentTimeMillis();
        try {
            filePath = getIntent().getStringExtra("path");
           // Log.e(TAG, "onCreate: " + filePath);
            FileInputStream fis = new FileInputStream(filePath);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트(Sheet) 수
            int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수 -- 전체 행 개수 가져옴

            mLabels = new String[rows - 1];
            mValues = new float[rows - 1];
            mLableValues = new float[rows - 1];
            for (int i = 0; i < (rows - 1); i++) {
                if (i == 4) {
                    mLabels[i] = "START";
                } else if (i == (rows - 4)) {
                    mLabels[i] = "END";
                } else {
                    mLabels[i] = "";
                    mValues[i] = 0.0f;
                }
            }

            String cellValue = null;
            float graphValue = 0;
            int cnt = 0;
            // TODO: 2018-02-22 행이동 반복문
            for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
                HSSFRow row = sheet.getRow(rowIndex); // 각 행을 읽어온다
                if (row != null) {
                    int cells = row.getPhysicalNumberOfCells();
                    //Log.e(TAG, "onPostExecute: // 각 행을 읽어온다 - " + cells);
                    // TODO: 2018-02-22 셀값을 각각 가져오는 부분
                    for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
                        HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
                        //Log.e(TAG, "onPostExecute: / 셀에 담겨있는 값을 읽는다. - " + cell);
                        if (cell != null) {
                            if (columnIndex == 0) {
                                //Log.e(TAG, "doInBackground:cellValue " + cellValue);
                                cellValue = cell.getStringCellValue();
                            }
                        } else {
                        }
                        //Log.e(TAG, "onPostExecute: value - " + value);
                    }
                }
                graphValue = Float.valueOf(cellValue);
                mLableValues[cnt] = graphValue;
                cnt++;
            }

            Arrays.sort(mLableValues);
            minYLabel =  mLableValues[0]  -  1;
            maxYLabel =  mLableValues[mLableValues.length - 1] + 1;

            // 최소값(Min) 출력
            //Log.e(TAG, "onCreate:/ 최소값(Min) 출력 " + mLableValues[0] );
            // 최대값(Max) 출력
            //Log.e(TAG, "onCreate:/ 최대값(Max) 출력 " + mLableValues[mLableValues.length - 1] );
            //System.out.println(mLabels[mLabels.length - 1]); // 결과: 2096.829

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        Log.e(TAG, "연산 시간 : " + (endTime - startTime));
        // TODO: 2018-02-22 william chart
        LineSet dataset = new LineSet(mLabels, mValues);
//        dataset.setColor(Color.parseColor("#004f7f"))
//                .setThickness(Tools.fromDpToPx(3))
//                .setSmooth(true);
        dataset.setColor(Color.parseColor("#ffc755")).setSmooth(true).setThickness(4);

        //.setDotsColor(Color.parseColor("#ffc755"))
        //.setFill(Color.parseColor("#2d374c"))
        // TODO: 2018-02-23 그래프 라운드 점 설정하는 함수
//        for (int i = 0; i < mLabels.length; i += 20) {
//            Point point = (Point) dataset.getEntry(i);
//            point.setColor(Color.parseColor("#ffc755"));
//            point.setStrokeColor(Color.parseColor("#0290c3"));
//            if (i == 30 || i == 10) point.setRadius(Tools.fromDpToPx(6));
//        }
        mChart.addData(dataset);

        //mChart.show(new Animation().setInterpolator(new BounceInterpolator()).fromAlpha(0));
        // TODO: 2018-02-22 그래프 그리드 추가 및 설정
        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#C0C0C0"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(0.75f));

        //mChart.setOnEntryClickListener((setIndex, entryIndex, rect) -> Toast.makeText(getApplicationContext(), "" + setIndex +" - "+ entryIndex, Toast.LENGTH_SHORT).show());

        mChart.setAxisBorderValues(minYLabel, maxYLabel)
                .setTooltips(mTip)
                .setGrid(0, 7, gridPaint)
                .show(new Animation().setInterpolator(new BounceInterpolator()).fromAlpha(0));
        //
        //.setYLabels(AxisRenderer.LabelPosition.NONE)


//        Paint thresPaint = new Paint();
//        thresPaint.setColor(Color.parseColor("#0079ae"));
//        thresPaint.setStyle(Paint.Style.STROKE);
//        thresPaint.setAntiAlias(true);
//        thresPaint.setStrokeWidth(Tools.fromDpToPx(.75f));
//        thresPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
//
//        Paint gridPaint = new Paint();
//        gridPaint.setColor(Color.parseColor("#153285"));
//        gridPaint.setStyle(Paint.Style.STROKE);
//        gridPaint.setAntiAlias(true);
//        gridPaint.setStrokeWidth(Tools.fromDpToPx(1f));
        //mChart.show();
//        mChart.setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
//                .setYLabels(AxisRenderer.LabelPosition.NONE)
//                .setGrid(0, 7, gridPaint)
//                .setValueThreshold(80f, 80f, thresPaint)
//                .setAxisBorderValues(0, 110)
//                .show();
//        mLineChar.setPaintShaderColors(Color.RED,
//                Color.parseColor("#E79D23"),
//                Color.parseColor("#FFF03D"),
//                Color.parseColor("#A9E16F"),
//                Color.parseColor("#75B9EF"));
//
//        mLineChar.setLineWidth(1.2f);
//        mLineChar.setLinePointRadio((int) mLineChar.getLineWidth());
//        mLineChar.setNormalColor(Color.parseColor("#676567"));
//        mLineChar.setSelectedMode(SELECETD_MSG_SHOW_TOP);
//        mLineChar.setLineStyle(JcoolGraph.LINE_CURVE);
//        mLineChar.setScrollAble(true);
//        //mLineChar.setLineShowStyle(JcoolGraph.LINESHOW_FROMLINE);
//        mLineChar.feedData(lines);

        //filePath = getIntent().getStringExtra("path");

        adapter = new DetailListAdapter(this, readFileList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
//        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(adapter);
//        slideInBottomAnimationAdapter.setDuration(500);
//        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
//        scaleInAnimationAdapter.setDuration(1000);
        recyclerView.setAdapter(alphaAdapter);
        //recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0 && fab.isShown()) {
                    fab.hide();
                }
            }
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(FilePickedActivity.this, GraphAnalysisActivity.class);
            if (!readFileList.isEmpty()) {
                if (Paper.book("filePicked").write("data", readFileList) != null) {
                    Paper.book("filePicked").delete("data");
                    Paper.book("filePicked").write("data", readFileList);
                } else {
                    Paper.book("filePicked").write("data", readFileList);
                }
            }

            intent.putExtra("page", 10);
            intent.putExtra("filepath",filePath);
            intent.putExtra("filename",filename);
            startActivity(intent);

        });
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String path;
        private static final String TAG = "BackgroundTask";
        //AlertDialog alertDialog;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(FilePickedActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("파일 읽는중...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
//            alertDialog = new SpotsDialog(getApplicationContext());
//            alertDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.e(TAG, "doInBackground: " + "호출 됬어요");
            //String abc = "Parsing & Download OK!!!";
            String cellValue = null, dateTime = null, stringNull;
            float graphValue = 0;
            int cnt = 0;
            long startTime = System.currentTimeMillis();
            try {
                path = getIntent().getStringExtra("path");
                //Log.e(TAG, "onPreExecute: " + path);
                FileInputStream fis = new FileInputStream(path);
                HSSFWorkbook workbook = new HSSFWorkbook(fis);
                HSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트(Sheet) 수
                int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수 -- 전체 행 개수 가져옴
                //Log.e(TAG, "onPostExecute: 해당 시트의 행의 개수 - " + rows);
                progressDialog.setMax(rows);
                // TODO: 2018-02-22 행이동 반복문
                for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
                    HSSFRow row = sheet.getRow(rowIndex); // 각 행을 읽어온다
                    if (row != null) {
                        int cells = row.getPhysicalNumberOfCells();
                        //Log.e(TAG, "onPostExecute: // 각 행을 읽어온다 - " + cells);
                        // TODO: 2018-02-22 셀값을 각각 가져오는 부분  
                        for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
                            String value = "";
                            HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
                            //Log.e(TAG, "onPostExecute: / 셀에 담겨있는 값을 읽는다. - " + cell);
                            if (cell != null) {
                                if (columnIndex == 0) {
                                    //Log.e(TAG, "doInBackground:cellValue " + cellValue);
                                    cellValue = cell.getStringCellValue();
                                } else if (columnIndex == 1) {
                                    //Log.e(TAG, "doInBackground: dateTime " + dateTime);
                                    dateTime = cell.getStringCellValue();
                                }
                            } else {
                                value = "unknown";
                            }

                           /* switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고 해당 타입에 맞게 가져온다.
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    value = cell.getNumericCellValue() + "";
                                    break;
                                case HSSFCell.CELL_TYPE_STRING:
                                    value = cell.getStringCellValue() + "";
                                    break;
                                case HSSFCell.CELL_TYPE_BLANK:
                                    value = cell.getBooleanCellValue() + "";
                                    break;
                                case HSSFCell.CELL_TYPE_ERROR:
                                    value = cell.getErrorCellValue() + "";
                                    break;
                            }*/
                            //Log.e(TAG, "onPostExecute: value - " + value);
                        }
                    }
                    graphValue = Float.valueOf(cellValue);
                    //Log.e(TAG, "doInBackground: graphValue " + graphValue);
                    readFileList.add(new RealTime(cellValue, dateTime));
                    graphLine.add(new Jchart(graphValue, Color.parseColor("#b8e986")));
                    progressDialog.setProgress(rowIndex);
                    mValues[cnt] = graphValue;
                    cnt++;
                    //Thread.sleep(20);
                }
               // Log.e(TAG, "doInBackground: mValues.length - " + mValues.length);
            } catch (IOException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            Log.e(TAG, "비동기 처리 시간 :  " + (endTime - startTime));
            //Log.e(TAG, "doInBackground: " + path);
            return path;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            adapter.notifyDataSetChanged();
            //Log.e(TAG, "onPostExecute: mValues.length - " + mValues.length);
            mChart.updateValues(0, mValues);
            mChart.notifyDataUpdate();
            //Log.e(TAG, "onPostExecute:  graphLine size - " + graphLine.size());
            //mLineChar.aniChangeData(graphLine);
            super.onPostExecute(s);
//            mLineChar.post(() -> mLineChar.aniChangeData(graphLine));
//            mLineChar.setPaintShaderColors(Color.RED,
//                    Color.parseColor("#E79D23"),
//                    Color.parseColor("#FFF03D"),
//                    Color.parseColor("#A9E16F"),
//                    Color.parseColor("#75B9EF"));
//
//            //mLineChar.setLineWidth(1.2f);
//           // mLineChar.setLinePointRadio((int) mLineChar.getLineWidth());
//            mLineChar.setNormalColor(Color.parseColor("#676567"));
//            mLineChar.setSelectedMode(SELECETD_MSG_SHOW_TOP);
//            mLineChar.setLineStyle(JcoolGraph.LINE_CURVE);
//            mLineChar.setLineShowStyle(JcoolGraph.LINESHOW_ASWAVE);
//            //mLineChar.feedData(lines);
            //Log.e(TAG, "onPostExecute:  called ");

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //Log.e(TAG, "onProgressUpdate: called ");
        }
    }
}
