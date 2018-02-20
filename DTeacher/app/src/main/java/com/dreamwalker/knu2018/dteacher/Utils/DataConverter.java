/**
 * Hexiwear application is used to pair with Hexiwear BLE devices
 * and send sensor readings to WolkSense sensor data cloud
 * <p>
 * Copyright (C) 2016 WolkAbout Technology s.r.o.
 * <p>
 * Hexiwear is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Hexiwear is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dreamwalker.knu2018.dteacher.Utils;

public class DataConverter {

//    private static final String INTEGER = "%d";
//    private static final String FLOAT = "%.0f";
//    private static final String TRIPLE_VALUE = "%+.0f%+.0f%+.0f";

    private DataConverter() {
        // Not meant to be instantiated.
    }

    public static String PatternDataConverter(byte[] data) {
        int start = data[0];
        int seq = data[1];
        int total = data[2];
        int patternSeq = ((int) data[3] << 24) | (data[4] << 16) | (data[5] << 8) | (data[6] & 0xFF);
        int type = data[7];
        int value = ((int) data[8] << 8) | (data[9] & 0xff);
        int year = ((int) data[10] << 8) | (data[11] & 0xff);
        int month = data[12];
        int day = data[13];
        int hour = data[14];
        int minute = data[15];
        int second = data[16];
        int r1 = data[17];
        int r2 = data[18];
        int end = data[19];
        String resultString = start + "," + seq + "," + total + "," + patternSeq + ","
                + type + "," + value + "," + year + "," + month + "," + day + ","
                + hour + "," + minute + "," + second + "," + r1 + "," + r2 + "," + end;
//        return String.format("%02x ;%02x ;%02x ", gyroXintVal, gyroYintVal, gyroZintVal);
        return resultString;
    }

    public static String DiabetesDataConverter(byte[] data) {
        int start = data[0];
        int seq = data[1];
        int total = data[2];
        int patternSeq = ((int) data[3] << 8) | (data[4] & 0xFF);
        int type = data[5];
        int value = ((int) data[6] << 8) | (data[7] & 0xff);
        int year = ((int) data[8] << 8) | (data[9] & 0xff);
        int month = data[10];
        int day = data[11];
        int hour = data[12];
        int minute = data[13];
        int second = data[14];
        int r1 = data[15];
        int r2 = data[16];
        int r3 = data[17];
        int r4 = data[18];
        int end = data[19];
        String resultString = start + "," + seq + "," + total + "," + patternSeq + ","
                + type + "," + value + "," + year + "," + month + "," + day + ","
                + hour + "," + minute + "," + second + "," + r1 + "," + r2 + "," + r3 + "," + r4 + "," + end;
//        return String.format("%02x ;%02x ;%02x ", gyroXintVal, gyroYintVal, gyroZintVal);
        return resultString;
    }
    public static String LightConverter(byte[] data) {
        float lightValue = (float) data[0] / 100;
        String resultString = String.valueOf(lightValue);
        return resultString;
    }


}
