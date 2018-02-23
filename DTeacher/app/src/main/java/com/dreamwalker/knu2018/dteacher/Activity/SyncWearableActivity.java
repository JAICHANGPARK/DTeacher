package com.dreamwalker.knu2018.dteacher.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.WearableSync.SyncWearableDataActivity;
import com.dreamwalker.knu2018.dteacher.WearableSync.WearableRealTimeActivity;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.*;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SyncWearableActivity extends AppCompatActivity {

    private static final String TAG = "SyncWearableActivity";

    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;

    // TODO: 2018-02-14 장비등록 버튼
    @BindView(R.id.buttonRegister)
    Button buttonRegister;
    @BindView(R.id.buttonRealTime)
    Button buttonRealTime;
    @BindView(R.id.buttonSync)
    Button buttonSync;
    @BindView(R.id.buttonFilePick)
    Button buttonFilePick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_wearable);
        setTitle("웨어러블 장비");
        ButterKnife.bind(this);

    }

    @OnClick(R.id.buttonRegister)
    public void onDeviceRegisterButtonClicked() {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(intent);
    }

    @OnClick(R.id.buttonRealTime)
    public void onRealTimeButtonClicked() {
        startActivity(new Intent(SyncWearableActivity.this, WearableRealTimeActivity.class));
    }

    @OnClick(R.id.buttonSync)
    public void onSyncButtonClicked() {
        startActivity(new Intent(SyncWearableActivity.this, SyncWearableDataActivity.class));
    }

    @OnClick(R.id.buttonFilePick)
    public void onFilePickerButtonClicked() {
        checkPermissionsAndOpenFilePicker();
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
            openFilePicker();
        }
    }

    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFilePicker();
                } else {
                    showError();
                }
            }
        }
    }

    private void openFilePicker() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getExternalFilesDir(null).toString());
        Log.e(TAG, "openFilePicker: " + Environment.getExternalStorageDirectory().toString());
        Log.e(TAG, "openFilePicker: getExternalFilesDir" + getExternalFilesDir(null).toString());
        //.withPath(Environment.getExternalStorageDirectory().toString() + "/myFolder")
        new MaterialFilePicker()
                .withActivity(this)
                .withPath(stringBuilder.toString())
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withTitle("로그 파일 선택")
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            String filename = new File(path).getName();
            Log.e(TAG, "onActivityResult: " + filename);

            if (path != null) {
                Log.e("Path: ", path);

                Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SyncWearableActivity.this, FilePickedActivity.class);
                intent.putExtra("path", path);
                intent.putExtra("filename",filename);
                startActivity(intent);
            }
        }
    }

}
