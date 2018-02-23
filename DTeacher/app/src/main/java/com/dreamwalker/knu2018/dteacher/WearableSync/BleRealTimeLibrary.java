package com.dreamwalker.knu2018.dteacher.WearableSync;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BleRealTimeLibrary extends Activity {

    private Context mainContext = this;

    public abstract void onConectionStateChange(connectionStateEnum theconnectionStateEnum);

    public abstract void onSerialReceived(String theString);

    public abstract void onSerialTrans(String theString);

    public void serialSend(String theString) {
        if (mConnectionState == connectionStateEnum.isConnected) {
            mSCharacteristic.setValue(theString);
            mBluetoothLeService.writeCharacteristic(mSCharacteristic);
        }
    }

    private int mBaudrate = 115200;    //set the default baud rate to 115200

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }

    private static BluetoothGattCharacteristic mSCharacteristic, mModelNumberCharacteristic, mSerialPortCharacteristic, mCommandCharacteristic;
    private static BluetoothGattCharacteristic mHexiCharacteristic, mHexiStartCharacteristic;
    private static BluetoothGattCharacteristic mHexiLightCharacteristic;
    private static BluetoothGattCharacteristic mHexiPressureCharacteristic;
    BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private LeDeviceListAdapter mLeDeviceListAdapter = null;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning = false;
    AlertDialog mScanDeviceDialog;
    private String mDeviceName;
    private String mDeviceAddress;

    public enum connectionStateEnum {isNull, isScanning, isToScan, isConnecting, isConnected, isDisconnecting}

    TimerTask readTimerTask;
    Timer myTimer = new Timer();

    public connectionStateEnum mConnectionState = connectionStateEnum.isNull;
    private static final int REQUEST_ENABLE_BT = 1;
    private Handler mHandler = new Handler();
    public boolean mConnected = false;
    private final static String TAG = BleRealTimeLibrary.class.getSimpleName();

    private Runnable mConnectingOverTimeRunnable = new Runnable() {

        @Override
        public void run() {
            if (mConnectionState == connectionStateEnum.isConnecting)
                mConnectionState = connectionStateEnum.isToScan;
            onConectionStateChange(mConnectionState);
            mBluetoothLeService.close();
        }
    };

    private Runnable mDisonnectingOverTimeRunnable = new Runnable() {

        @Override
        public void run() {
            if (mConnectionState == connectionStateEnum.isDisconnecting)
                mConnectionState = connectionStateEnum.isToScan;
            onConectionStateChange(mConnectionState);
            mBluetoothLeService.close();
        }
    };

    public static final String SerialPortUUID = "0000dfb1-0000-1000-8000-00805f9b34fb";
    public static final String CommandUUID = "0000dfb2-0000-1000-8000-00805f9b34fb";
    public static final String ModelNumberStringUUID = "00002a24-0000-1000-8000-00805f9b34fb";

    // TODO: 2018-01-24 헥시웨어 전용
    public static final String HexiStartUUID = "00002011-0000-1000-8000-00805f9b34fb";
    public static final String HexiStringUUID = "00002032-0000-1000-8000-00805f9b34fb";


    public void onCreateProcess() {
        if (!initiate()) {
            Toast.makeText(mainContext, R.string.error_bluetooth_not_supported,
                    Toast.LENGTH_SHORT).show();
            ((Activity) mainContext).finish();
        }

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        mainContext.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();
        // Initializes and show the scan Device Dialog
        mScanDeviceDialog = new AlertDialog.Builder(mainContext)
                .setTitle("BLE Device Scan...")
                .setAdapter(mLeDeviceListAdapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final BluetoothDevice device = mLeDeviceListAdapter.getDevice(which);
                        if (device == null)
                            return;
                        scanLeDevice(false);

                        if (device.getName() == null || device.getAddress() == null) {
                            mConnectionState = connectionStateEnum.isToScan;
                            onConectionStateChange(mConnectionState);
                        } else {

                            System.out.println("onListItemClick " + device.getName().toString());

                            System.out.println("Device Name:" + device.getName() + "   " + "Device Name:" + device.getAddress());

                            mDeviceName = device.getName().toString();
                            mDeviceAddress = device.getAddress().toString();

                            if (mBluetoothLeService.connect(mDeviceAddress)) {
                                Log.d(TAG, "Connect request success");
                                mConnectionState = connectionStateEnum.isConnecting;
                                onConectionStateChange(mConnectionState);
                                mHandler.postDelayed(mConnectingOverTimeRunnable, 10000);
                            } else {
                                Log.d(TAG, "Connect request fail");
                                mConnectionState = connectionStateEnum.isToScan;
                                onConectionStateChange(mConnectionState);
                            }
                        }
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface arg0) {
                        System.out.println("mBluetoothAdapter.stopLeScan");

                        mConnectionState = connectionStateEnum.isToScan;
                        onConectionStateChange(mConnectionState);
                        mScanDeviceDialog.dismiss();

                        scanLeDevice(false);
                    }
                }).create();

    }

    public void onResumeProcess() {
        System.out.println("BlUNOActivity onResume");
        // Ensures Bluetooth is enabled on the device. If Bluetooth is not
        // currently enabled,
        // fire an intent to display a dialog asking the user to grant
        // permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                ((Activity) mainContext).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        // TODO: 2018-02-23 원래 이쪽에서 등록함 --> 온크리트로
        //mainContext.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }

    public void onPauseProcess() {
        System.out.println("BLUNOActivity onPause");
        scanLeDevice(false);
        mainContext.unregisterReceiver(mGattUpdateReceiver);
        mLeDeviceListAdapter.clear();
        mConnectionState = connectionStateEnum.isToScan;
        onConectionStateChange(mConnectionState);
        mScanDeviceDialog.dismiss();
        if (mBluetoothLeService != null) {
            mBluetoothLeService.disconnect();
            mHandler.postDelayed(mDisonnectingOverTimeRunnable, 10000);

//			mBluetoothLeService.close();
        }
        mSCharacteristic = null;

    }

    public void onStopProcess() {
        System.out.println("MiUnoActivity onStop");
        if (mBluetoothLeService != null) {
//			mBluetoothLeService.disconnect();
//            mHandler.postDelayed(mDisonnectingOverTimeRunnable, 10000);
            mHandler.removeCallbacks(mDisonnectingOverTimeRunnable);
            mBluetoothLeService.close();
            readCharStop();
        }
        mSCharacteristic = null;
    }

    public void onDestroyProcess() {
        mainContext.unbindService(mServiceConnection);
        mBluetoothLeService = null;
        readCharStop();
    }

    public void onActivityResultProcess(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT
                && resultCode == Activity.RESULT_CANCELED) {
            ((Activity) mainContext).finish();
            return;
        }
    }

    boolean initiate() {
        // Use this check to determine whether BLE is supported on the device.
        // Then you can
        // selectively disable BLE-related features.
        if (!mainContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            return false;
        }

        // Initializes a Bluetooth adapter. For API level 18 and above, get a
        // reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) mainContext.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            return false;
        }
        return true;
    }

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.


    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @SuppressLint("DefaultLocale")
        @Override
        public void onReceive(Context context, final Intent intent) {
            final String action = intent.getAction();
            System.out.println("mGattUpdateReceiver->onReceive->action=" + action);
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                mHandler.removeCallbacks(mConnectingOverTimeRunnable);

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                mConnectionState = connectionStateEnum.isToScan;
                onConectionStateChange(mConnectionState);
                mHandler.removeCallbacks(mDisonnectingOverTimeRunnable);
                mBluetoothLeService.close();
                readCharStop();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                for (BluetoothGattService gattService : mBluetoothLeService.getSupportedGattServices()) {
                    System.out.println("ACTION_GATT_SERVICES_DISCOVERED  " + gattService.getUuid().toString());
                }
                getGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {

                // TODO: 2018-01-24 이쪽에서 받은 데이터 처리한다.
                if (mSCharacteristic == mHexiLightCharacteristic) {
                    //mBluetoothLeService.readCharacteristic(mSCharacteristic);
                    mConnectionState = connectionStateEnum.isConnected;
                    onConectionStateChange(mConnectionState);
                    mBluetoothLeService.setCharacteristicNotification(mSCharacteristic, true);
                    String tmpString = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                    onSerialReceived(tmpString);
                }

                
                if (mSCharacteristic == mHexiPressureCharacteristic){
                    mConnectionState = connectionStateEnum.isConnected;
                    onConectionStateChange(mConnectionState);
                    mBluetoothLeService.setCharacteristicNotification(mSCharacteristic, true);
                    String tmpString = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                    onSerialReceived(tmpString);
                }
                System.out.println("displayData " + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    void buttonScanOnClickProcess() {
        switch (mConnectionState) {
            case isNull:
                mConnectionState = connectionStateEnum.isScanning;
                onConectionStateChange(mConnectionState);
                scanLeDevice(true);
                mScanDeviceDialog.show();
                break;
            case isToScan:
                mConnectionState = connectionStateEnum.isScanning;
                onConectionStateChange(mConnectionState);
                scanLeDevice(true);
                mScanDeviceDialog.show();
                break;
            case isScanning:
                break;
            case isConnecting:
                break;
            case isConnected:
                mBluetoothLeService.disconnect();
                mHandler.postDelayed(mDisonnectingOverTimeRunnable, 10000);
//			mBluetoothLeService.close();
                mConnectionState = connectionStateEnum.isDisconnecting;
                onConectionStateChange(mConnectionState);
                break;
            case isDisconnecting:
                break;
            default:
                break;
        }
    }

    void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.

            System.out.println("mBluetoothAdapter.startLeScan");

            if (mLeDeviceListAdapter != null) {
                mLeDeviceListAdapter.clear();
                mLeDeviceListAdapter.notifyDataSetChanged();
            }

            if (!mScanning) {
                mScanning = true;
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            }
        } else {
            if (mScanning) {
                mScanning = false;
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }
    }

    // Code to manage Service lifecycle.
    ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            System.out.println("mServiceConnection onServiceConnected");
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                ((Activity) mainContext).finish();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.println("mServiceConnection onServiceDisconnected");
            mBluetoothLeService = null;
        }
    };

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi,
                             byte[] scanRecord) {
            ((Activity) mainContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("mLeScanCallback onLeScan run ");
                    mLeDeviceListAdapter.addDevice(device);
                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    // TODO: 2018-02-23 서비스 특성을 하나식 분해해 변수에 넣는 부분 중요.
    private void getGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;

        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
        mModelNumberCharacteristic = null;
        mSerialPortCharacteristic = null;
        mCommandCharacteristic = null;
        mHexiCharacteristic = null;
        mHexiStartCharacteristic = null;
        mHexiLightCharacteristic = null;
        mHexiPressureCharacteristic = null;

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            uuid = gattService.getUuid().toString();
            System.out.println("displayGattServices + uuid=" + uuid);

            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                uuid = gattCharacteristic.getUuid().toString();
                if (uuid.equals(ModelNumberStringUUID)) {
                    mModelNumberCharacteristic = gattCharacteristic;
                    System.out.println("mModelNumberCharacteristic  " + mModelNumberCharacteristic.getUuid().toString());
                } else if (uuid.equals(SerialPortUUID)) {
                    mSerialPortCharacteristic = gattCharacteristic;
                    System.out.println("mSerialPortCharacteristic  " + mSerialPortCharacteristic.getUuid().toString());
//                    updateConnectionState(R.string.comm_establish);
                } else if (uuid.equals(CommandUUID)) {
                    mCommandCharacteristic = gattCharacteristic;
                    System.out.println("mSerialPortCharacteristic  " + mSerialPortCharacteristic.getUuid().toString());
//                    updateConnectionState(R.string.comm_establish);
                }
                // TODO: 2018-02-20 광센서 실시간 데이터 받기. 
                else if (uuid.equals(IntentConst.HEXI_AMBILITE_UUID)) {
                    mHexiLightCharacteristic = gattCharacteristic;
                    System.out.println("HexiStartCharacteristic  " + mHexiLightCharacteristic.getUuid().toString());
                }
                else if (uuid.equals(IntentConst.HEXI_PRESSURE_UUID)){
                    mHexiPressureCharacteristic = gattCharacteristic;
                    Log.e(TAG, "getGattServices:  mHexiPressureCharacteristic - " +  mHexiPressureCharacteristic.getUuid().toString());
                }

                else if (uuid.equals(HexiStartUUID)) {
                    mHexiStartCharacteristic = gattCharacteristic;
                    System.out.println("HexiStartCharacteristic  " + mHexiStartCharacteristic.getUuid().toString());
//                    updateConnectionState(R.string.comm_establish);
                } else if (uuid.equals(HexiStringUUID)) {
                    mHexiCharacteristic = gattCharacteristic;
                    System.out.println("HexiCharacteristic  " + mHexiCharacteristic.getUuid().toString());
//                    updateConnectionState(R.string.comm_establish);
                }
            }
            mGattCharacteristics.add(charas);
        }

        if (mHexiLightCharacteristic == null) {
            Toast.makeText(mainContext, "Please select wearable devices", Toast.LENGTH_SHORT).show();
            mConnectionState = connectionStateEnum.isToScan;
            onConectionStateChange(mConnectionState);
        } else {
            mSCharacteristic = mHexiLightCharacteristic;
            //mBluetoothLeService.setCharacteristicNotification(mSCharacteristic, true);
            mBluetoothLeService.readCharacteristic(mSCharacteristic);
            //readCharStart(1000, mSCharacteristic);
        }

        if (mHexiPressureCharacteristic != null){
            mSCharacteristic = mHexiPressureCharacteristic;
            //mBluetoothLeService.setCharacteristicNotification(mSCharacteristic, true);
            mBluetoothLeService.readCharacteristic(mSCharacteristic);
            // TODO: 2018-02-23 관성계의 경우 인터벌 짤ㅂ게 해야 100ms
            readCharStart(200, mSCharacteristic);
        }else {
            Toast.makeText(mainContext, "Please select wearable devices", Toast.LENGTH_SHORT).show();
            mConnectionState = connectionStateEnum.isToScan;
            onConectionStateChange(mConnectionState);
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    // TODO: 2018-02-20 반복적 읽기를 위한 타이머 테스크 !  
    public TimerTask timerTaskMaker(final BluetoothGattCharacteristic characteristic) {
        final TimerTask tempTimeTask = new TimerTask() {
            @Override
            public void run() {
                if (characteristic != null) {
                    Log.e(TAG, "timerTaskMaker: mSCharacteristic2" + characteristic.getUuid().toString());
                    bleReadCharacteristic(characteristic);
                }
            }
        };
        return tempTimeTask;
    }

    // TODO: 2018-02-20 서비스가 발견되면 타이머 스케쥴링.
    public void readCharStart(long interval, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
//        if (mSCharacteristic == null) {
//            mSCharacteristic = mHexiLightCharacteristic;
//        }
        if (mHexiLightCharacteristic != null){
            mSCharacteristic = mHexiLightCharacteristic;
        }

        if (mHexiPressureCharacteristic != null){
            mSCharacteristic = mHexiPressureCharacteristic;
        }
        //myTimer = new Timer();
        readTimerTask = timerTaskMaker(bluetoothGattCharacteristic);
        //ReadCharTask readCharTask = new ReadCharTask();
        myTimer.schedule(readTimerTask, 100, interval);
    }

    /**
     * 타이머를 cancel 하지 않고 타이머 테스크를 cancel 하면 됩니다.
     *
     * @param : NONE
     * @return : NONE
     * @author : JAICHANGPARK.
     */
    public void readCharStop() {
        // TODO: 2018-02-13 NUll 예외 처리
        if (readTimerTask != null){
            readTimerTask.cancel();
            Log.e(TAG, "readCharStop: scheduledExecutionTime ." + readTimerTask.scheduledExecutionTime());
        }

    }

    /**
     * @param characteristic
     */
    public void bleReadCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (characteristic != null) {
            final int charaProp = characteristic.getProperties();
            Log.e(TAG, "bleReadCharacteristic: charaProp : " + charaProp);
            if ((charaProp & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                while (!mBluetoothLeService.readCharacteristic(characteristic)) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "InterruptedException");
                    }
                }
            }
        }
    }

    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = ((Activity) mainContext).getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if (!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                System.out.println("mInflator.inflate  getView");
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.deviceName.setText(deviceName);
            else
                viewHolder.deviceName.setText(R.string.unknown_device);
            viewHolder.deviceAddress.setText(device.getAddress());

            return view;
        }
    }
}
