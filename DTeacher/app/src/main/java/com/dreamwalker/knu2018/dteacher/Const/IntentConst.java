package com.dreamwalker.knu2018.dteacher.Const;

import java.util.UUID;

/**
 * Created by KNU2017 on 2018-02-01.
 */

public class IntentConst {

    public static final String SIGNUP_EXTRA_DATA_0 = "SIGNUP_EXTRA_DATA_0";
    public static final String SIGNUP_EXTRA_DATA_1 = "SIGNUP_EXTRA_DATA_1";
    public static final String SIGNUP_EXTRA_DATA_2 = "SIGNUP_EXTRA_DATA_2";
    public static final String SIGNUP_EXTRA_DATA_3 = "SIGNUP_EXTRA_DATA_3";
    public static final String SIGNUP_EXTRA_DATA_4 = "SIGNUP_EXTRA_DATA_4";
    public static final String SIGNUP_EXTRA_DATA_5 = "SIGNUP_EXTRA_DATA_5";
    public static final String SIGNUP_EXTRA_DATA_6 = "SIGNUP_EXTRA_DATA_6";
    public static final String SIGNUP_REGISTER_TYPE = "SIGNUP_REGISTER_TYPE";

    public static final String DIARY_PAGE_FRAGMENT_NUM = "DIARY_PAGE_FRAGMENT_NUM";

    public static final String WRITE_DRUG_TYPE = "WRITE_DRUG_TYPE";
    public static final String WEB_URL = "WEB_URL";

    // TODO: 2018-02-19 혈당계 인텐트 상수 및 UUID 정의
    public final static int BLE_SCAN_DURATION = 5000;
    public final static String INTENT_BLE_EXTRA_DATA = "com.dreamwalker.knu2018.dteacher.standard.ble.BLE_EXTRA_DATA";
    public final static String INTENT_BLE_CONNECTED_DEVICE = "com.dreamwalker.knu2018.dteacher.standard.ble.BLE_CONNECTED_DEVICE";
    public final static String INTENT_BLE_BONDED = "com.dreamwalker.knu2018.dteacher.ble.BLE_BONDED";
    public final static String INTENT_BLE_BOND_NONE = "com.dreamwalker.knu2018.dteacher.ble.BLE_BOND_NONE";
    public final static String INTENT_BLE_DEVICECONNECTED = "com.dreamwalker.knu2018.dteacher.ble.BLE_DEVICECONNECTED";
    public final static String INTENT_BLE_DEVICEDISCONNECTED = "com.dreamwalker.knu2018.dteacher.ble.BLE_DEVICEDISCONNECTED";
    public final static String INTENT_BLE_SERVICEDISCOVERED = "com.dreamwalker.knu2018.dteacher.ble.BLE_SERVICEDISCOVERED";
    public final static String INTENT_BLE_ERROR = "com.dreamwalker.knu2018.dteacher.ble.BLE_ERROR";
    public final static String INTENT_BLE_DEVICENOTSUPPORTED = "com.dreamwalker.knu2018.dteacher.ble.BLE_DEVICENOTSUPPORTED";
    public final static String INTENT_BLE_OPERATESTARTED = "com.dreamwalker.knu2018.dteacher.ble.BLE_OPERATESTARTED";
    public final static String INTENT_BLE_OPERATECOMPLETED = "com.dreamwalker.knu2018.dteacher.ble.BLE_OPERATECOMPLETED";
    public final static String INTENT_BLE_OPERATEFAILED = "com.dreamwalker.knu2018.dteacher.ble.BLE_OPERATEFAILED";
    public final static String INTENT_BLE_OPERATENOTSUPPORTED = "com.dreamwalker.knu2018.dteacher.ble.BLE_OPERATENOTSUPPORTED";
    public final static String INTENT_BLE_DATASETCHANGED = "com.dreamwalker.knu2018.dteacher.ble.BLE_DATASETCHANGED";
    public final static String INTENT_BLE_READ_SERIALNUMBER = "com.dreamwalker.knu2018.dteacher.ble.BLE_READ_SERIALNUMBER";
    public final static String INTENT_BLE_READ_MANUFACTURER = "com.dreamwalker.knu2018.dteacher.ble.BLE_READ_MANUFACTURER";
    public final static String INTENT_BLE_READ_SOFTWARE_REV = "com.dreamwalker.knu2018.dteacher.ble.BLE_READ_SOFTWARE_REVISION";
    public final static String INTENT_BLE_RACPINDICATIONENABLED = "com.dreamwalker.knu2018.dteacher.ble.BLE_RACPINDICATIONENABLED";
    public final static String INTENT_BLE_SEQUENCECOMPLETED = "com.dreamwalker.knu2018.dteacher.ble.BLE_SEQUENCECOMPLETED";
    public final static String INTENT_BLE_REQUEST_COUNT = "com.dreamwalker.knu2018.dteacher.ble.BLE_REQUESTCOUNT";
    public final static String INTENT_BLE_READCOMPLETED = "com.dreamwalker.knu2018.dteacher.ble.BLE_READCOMPLETED";
    // TODO: 2018-02-19 혈당계 서비스 정의 
    //Service
    public final static UUID BLE_SERVICE_GLUCOSE = UUID.fromString("00001808-0000-1000-8000-00805f9b34fb");
    public final static UUID BLE_SERVICE_DEVICE_INFO = UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb");
    public final static UUID BLE_SERVICE_CUSTOM = UUID.fromString("0000FFF0-0000-1000-8000-00805f9b34fb");
    // TODO: 2018-02-19 혈당계 특성 정의  
    //Characteristic
    public final static UUID BLE_CHAR_GLUCOSE_SERIALNUM = UUID.fromString("00002A25-0000-1000-8000-00805f9b34fb");
    public final static UUID BLE_CHAR_SOFTWARE_REVISION = UUID.fromString("00002A28-0000-1000-8000-00805f9b34fb");
    public final static UUID BLE_CHAR_GLUCOSE_MEASUREMENT = UUID.fromString("00002A18-0000-1000-8000-00805f9b34fb");
    public final static UUID BLE_CHAR_GLUCOSE_CONTEXT = UUID.fromString("00002A34-0000-1000-8000-00805f9b34fb");
    public final static UUID BLE_CHAR_GLUCOSE_RACP = UUID.fromString("00002A52-0000-1000-8000-00805f9b34fb");
    public final static UUID BLE_CHAR_CUSTOM_TIME = UUID.fromString("0000FFF1-0000-1000-8000-00805f9b34fb");
    //Descriptor
    public final static UUID BLE_DESCRIPTOR_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

}
