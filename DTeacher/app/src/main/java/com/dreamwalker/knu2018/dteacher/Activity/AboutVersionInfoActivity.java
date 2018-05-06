package com.dreamwalker.knu2018.dteacher.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamwalker.knu2018.dteacher.BuildConfig;
import com.dreamwalker.knu2018.dteacher.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.jsoup.Jsoup;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;

public class AboutVersionInfoActivity extends AppCompatActivity {
    private static final String TAG = "VersionInfoActivity";

    // Remote Config keys
    private static final String LOADING_PHRASE_CONFIG_KEY = "loading_phrase";
    private static final String WELCOME_MESSAGE_KEY = "welcome_message";
    private static final String WELCOME_MESSAGE_CAPS_KEY = "welcome_message_caps";
    private static final String APP_VERSION_KEY = "app_version";


    @BindView(R.id.versionImageView)
    ImageView versionImageView;
    @BindView(R.id.versionText)
    TextView versionText;
    @BindView(R.id.versionButton)
    FButton versionButton;

    String versionNumber;
    String deviceVersion;
    String storeVersion;
    PackageInfo i = null;

    Context mContext;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    //private BackgroundTask backgroundTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_version_info);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.app_icon_hr).into(versionImageView);


        try {
            i = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionNumber = i.versionName;
            versionText.setText(versionNumber);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        checkGooglePlayServices();
//
//        backgroundTask = new BackgroundTask();
//        backgroundTask.start();
        //initializeFirebase();

        // Get Remote Config instance.
        // [START get_remote_config_instance]
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // [END get_remote_config_instance]

        /// Create a Remote Config Setting to enable developer mode, which you can use to increase
        // the number of fetches available per hour during development. See Best Practices in the
        // README for more information.
        // [START enable_dev_mode]
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        // [END enable_dev_mode]

        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        fetchWelcome();
        //VersionChecker versionChecker = new VersionChecker();

        //new VersionChecker().execute();

//        String mLatestVersionName = null;
//        try {
//            mLatestVersionName = versionChecker.execute();
//            Log.e(TAG, "onCreate: " + mLatestVersionName );
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

//        String playStoreVersionCode = mFirebaseRemoteConfig.getString("app_version");
//        //FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
//        //String playStoreVersionCode = remoteConfig.getString("app_version");
//
//        Log.e(TAG, "playStoreVersionCode: " + playStoreVersionCode);
//        PackageInfo pInfo = null;
//        try {
//            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        String currentAppVersionCode = pInfo.versionName;
//        Log.e(TAG, "currentAppVersionCode: " + currentAppVersionCode);
//        if(playStoreVersionCode.equals(currentAppVersionCode)){
////Show update popup or whatever best for you
//            Log.e(TAG, "업데이트 필요없음 ");
//        }else {
//            Log.e(TAG, "onCreate: 업데이트 필요함."  );
//        }

    }

    /**
     * Fetch a welcome message from the Remote Config service, and then activate it.
     */
    private void fetchWelcome() {
        //mWelcomeTextView.setText(mFirebaseRemoteConfig.getString(LOADING_PHRASE_CONFIG_KEY));

        long cacheExpiration = 3600; // 1 hour in seconds.
        // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
        // will use fetch data from the Remote Config service, rather than cached parameter values,
        // if cached parameter values are more than cacheExpiration seconds old.
        // See Best Practices in the README for more information.
        mFirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AboutVersionInfoActivity.this, "Fetch Succeeded", Toast.LENGTH_SHORT).show();

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(AboutVersionInfoActivity.this, "Fetch Failed", Toast.LENGTH_SHORT).show();
                        }
                        displayWelcomeMessage();
                    }
                });
        // [END fetch_config_with_callback]
    }

    /**
     * Display a welcome message in all caps if welcome_message_caps is set to true. Otherwise,
     * display a welcome message as fetched from welcome_message.
     */
    // [START display_welcome_message]
    private void displayWelcomeMessage() {
        // [START get_config_values]
        String playstoreVersion = mFirebaseRemoteConfig.getString(APP_VERSION_KEY);
        Log.e(TAG, "displayWelcomeMessage: " + playstoreVersion );

        if (versionNumber.equals(playstoreVersion)){
            Log.e(TAG, "displayWelcomeMessage: " );

        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light));
            builder.setTitle("New version available");
            builder.setMessage("Please, update app to new version");
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("No, thanks", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();
        }


        // [END get_config_values]
//        if (mFirebaseRemoteConfig.getBoolean(WELCOME_MESSAGE_CAPS_KEY)) {
//            mWelcomeTextView.setAllCaps(true);
//        } else {
//            mWelcomeTextView.setAllCaps(false);
//        }
        //mWelcomeTextView.setText(welcomeMessage);
    }
    // [END display_welcome_message]



    public void initializeFirebase() {
        if (FirebaseApp.getApps(mContext).isEmpty()) {
            FirebaseApp.initializeApp(mContext, FirebaseOptions.fromResource(mContext));
        }
        final FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        config.setConfigSettings(configSettings);
    }


//    private final CheckHandler checkHandler = new CheckHandler(this);

    public class VersionChecker extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + AboutVersionInfoActivity.this.getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(7)
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);

            Log.e("update", "playstore version " + onlineVersion);
        }
    }

    private void checkGooglePlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (status != ConnectionResult.SUCCESS) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, status, -1);

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                }});
            dialog.show();

            googleApiAvailability.showErrorNotification(this, status);
        }
    }




//    public class VersionChecker extends AsyncTask<String, String, String> {
//
//        private String newVersion;
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            try {
//                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "com.dreamwalker.knu2018.dteacher" + "&hl=en")
//                        .timeout(30000)
//                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                        .referrer("http://www.google.com")
//                        .get()
//                        .select(".BgcNfc")
//                        .get(3)
//                        .ownText();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return newVersion;
//        }
//    }

//    public class VersionChecker extends AsyncTask<String, Document, Document> {
//
//        private Document document;
//
//        @Override
//        protected Document doInBackground(String... params) {
//
//            try {
//                document = Jsoup.connect("https://play.google.com/store/apps/details?id="+ "com.dreamwalker.knu2018.dteacher" +"&hl=en")
//                        .timeout(30000)
//                        .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
//                        .get();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return document;
//        }
//
//        @Override
//        protected void onPostExecute(Document d) {
//            super.onPostExecute(d);
//
//            Elements es =  d.body().getElementsByClass("xyOfqd").select(".hAyfc");
//            String newVersion = es.get(3).child(1).child(0).child(0).ownText();
//            Log.i(TAG, "newVersion==="+newVersion);
//        }
//    }


//    public class BackgroundTask extends Thread{
//
//        @Override
//        public void run() {
//
//            storeVersion = MarketVersionChecker.getMarketVersionFast(getPackageName());
//            Log.e(TAG, "run: storeVersion " + storeVersion);
//            try {
//                 deviceVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//
//                Log.e(TAG, "run: deviceVersion " + deviceVersion);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            checkHandler.sendMessage(checkHandler.obtainMessage());
//        }
//    }

//    private static class CheckHandler extends Handler{
//
//        WeakReference<AboutVersionInfoActivity> weakReference;
//
//        public CheckHandler(AboutVersionInfoActivity activity) {
//            weakReference = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            AboutVersionInfoActivity activity = weakReference.get();
//            if (activity != null){
//                activity.handleMessage(msg);
//            }
//        }
//    }
//
//    private void handleMessage(Message msg){
//
//
//        if (storeVersion.compareTo(deviceVersion) > 0) {
//            // 업데이트 필요
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light));
//            builder.setTitle("update");
//            builder.setMessage("New Version Released");
//            builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//            AlertDialog alertDialog = builder.create();
//            alertDialog.setCanceledOnTouchOutside(true);
//            alertDialog.show();
//
//        } else {
//            // 업데이트 불필요
//
//        }
//
//    }

}
