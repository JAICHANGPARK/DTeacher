package com.dreamwalker.knu2018.dteacher.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dreamwalker.knu2018.dteacher.R;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

import dmax.dialog.SpotsDialog;

public class FilePickedActivity extends AppCompatActivity {
    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picked);

        //filePath = getIntent().getStringExtra("path");
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
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("파일 읽는중...");
            progressDialog.show();
            super.onPreExecute();
//            alertDialog = new SpotsDialog(getApplicationContext());
//            alertDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.e(TAG, "doInBackground: " + "호출 됬어요");
            //String abc = "Parsing & Download OK!!!";
            try {
                path = getIntent().getStringExtra("path");
                Log.e(TAG, "onPreExecute: " + path);
                FileInputStream fis = new FileInputStream(path);
                HSSFWorkbook workbook = new HSSFWorkbook(fis);
                HSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트(Sheet) 수
                int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
                Log.e(TAG, "onPostExecute: 해당 시트의 행의 개수 - " + rows);
                for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
                    HSSFRow row = sheet.getRow(rowIndex); // 각 행을 읽어온다
                    if (row != null) {
                        int cells = row.getPhysicalNumberOfCells();
                        Log.e(TAG, "onPostExecute: // 각 행을 읽어온다 - " + cells);
                        for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
                            HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
                            Log.e(TAG, "onPostExecute: / 셀에 담겨있는 값을 읽는다. - " + cell);
                            String value = "";
                            if (cell != null) {
                                value = cell.getStringCellValue() + "";
                            } else {

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
                            Log.e(TAG, "onPostExecute: value - " + value);
                        }
                    }
                    Thread.sleep(500);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.e(TAG, "doInBackground: " + path);
            return path;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            super.onPostExecute(s);
            Log.e(TAG, "onPostExecute:  called ");
//            try {
//                //alertDialog.show();
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            //alertDialog.dismiss();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.e(TAG, "onProgressUpdate: called ");
        }
    }
}
