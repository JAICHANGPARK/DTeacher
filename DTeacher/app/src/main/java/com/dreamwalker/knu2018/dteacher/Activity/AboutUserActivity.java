package com.dreamwalker.knu2018.dteacher.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.R;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUserActivity extends AppCompatActivity {

    private static final String TAG = "AboutUserActivity";

    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;

    @BindView(R.id.buttonMyInfo)
    Button buttonMyInfo;
    @BindView(R.id.buttonExportDB)
    Button buttonExportDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user);
        ButterKnife.bind(this);


        // TODO: 2017-11-17 BLE 관련 Permission 주기
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs external permission");
                //builder.setMessage("Please grant location access so this app can detect beacons & receive monitoring data.");
                builder.setMessage("데이터베이스를 추출하기위해 권한허가가 필요합니다.");
                builder.setPositiveButton("Ok", null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    }
                });
                builder.show();
            }
        }



    }

    @OnClick(R.id.buttonMyInfo)
    public void onMyInfoButtonClicked() {
        startActivity(new Intent(AboutUserActivity.this, AboutUserInfoActivity.class));
    }

    @OnClick(R.id.buttonExportDB)
    public void onExportDBClicked() {
//        checkPermissionsAndOpenFilePicker();
        exportSqlite();
        Toast.makeText(this, "DB Export Complete!!", Toast.LENGTH_SHORT).show();
        zipFile();
    }

    // TODO: 2018-05-06  파일 압축하는 코드, 꼭 외부 파일 읽기 쓰기 권한 확인하기 .
    private void zipFile() {
        //Calendar now = Calendar.getInstance(Locale.KOREA);
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.KOREA);
        //String nowDateTime = simpleDateFormat.format(now.getTime());
        File data = Environment.getExternalStorageDirectory();
//        String zipFileName = "export_" + nowDateTime + ".zip";
//        String zipFileName = "export_" + nowDateTime + ".zip";
        String zipFileName = "export_db.zip";
        String backupDBPath = "/DTeacher/data/files/";
//        String backupDBPath = "/Android/data/com.dreamwalker.knu2018.dteacher/files/";
        String zipPath = "/DTeacher/data/files/" + zipFileName;

        ZipUtil.pack(new File(data, backupDBPath), new File(data, zipPath));

        Snackbar.make(getWindow().getDecorView().getRootView(), "압축완료", Snackbar.LENGTH_SHORT)
                .setAction("공유", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String fileName = getIntent().getStringExtra("filename");
                        File zipFile = new File(data, zipPath);
                        Uri path = Uri.fromFile(zipFile);
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("application/zip");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, path);
                        startActivity(Intent.createChooser(shareIntent, "데이터베이스공유"));
                    }
                }).show();
    }

    // TODO: 2018-05-06 데이터베이스 추출하는 코드
    public void sqliteExport(String dbName, String exportFileName) {
        //Context ctx = this; // for Activity, or Service. Otherwise simply get the context.
        //String dbname = "mydb.db";
        // dbpath = ctx.getDatabasePath(dbname);
        try {
            File data = Environment.getDataDirectory();
            File sd = Environment.getExternalStorageDirectory();

            Log.e(TAG, "getDataDirectory:  - " + data.toString());
            Log.e(TAG, "getExternalStorageDirectory:  - " + sd.toString());

            if (sd.canWrite()) {
                String currentDBPath = "/data/com.dreamwalker.knu2018.dteacher/databases/" + dbName;
                // TODO: 2018-05-06   "/Android/data/com.dreamwalker.knu2018.dteacher/files/" + exportFileName;
                String backupDBPath = "/DTeacher/data/files/" + exportFileName;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                if (backupDB.exists()) {
                    //Toast.makeText(this, "DB Export Complete!!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            //openFilePicker();
        }
    }


    /**
     * @Author : JAICHANGPARK
     * @Date : 2018-05-06
     * 외부 디렉토리에 파일 경로를 만드는 메소드.
     *
     */
    private void createDir(){
        boolean result = false;
        File sd = Environment.getExternalStorageDirectory();
        File file = new File(sd,"/DTeacher/data/files");

        if (!file.exists()){
            result = file.mkdirs();
        }else {
            Log.e(TAG, "createDir: asdfds" );
        }
        Log.e(TAG, "createDir: " + result);

    }

    private void exportSqlite(){
        sqliteExport("bs.db","bs.sqlite");
        sqliteExport("drug.db","drug.sqlite");
        sqliteExport("fitness.db","fitness.sqlite");
        sqliteExport("meal.db","meal.sqlite");
        sqliteExport("sleep.db","sleep.sqlite");
    }

    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }

    // TODO: 2017-11-17 마쉬멜로우 이상부터 권한 설정이 꼭 필요하다.
    // TODO: 2017-11-17 권한이 설정되지 않으면 블루투스 스캔이 되지 않는다. 그래서 사용자의 권한을 꼭 얻어야 한다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    createDir();
                    Log.e("permission", "coarse location permission granted");
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
