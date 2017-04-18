/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.gwnu.finalproject;

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    public static String TEMPERATURE_MEASURMENT = "00002a6e-0000-1000-8000-00805f9b34fb";
    public static String HUMIDITY_MEASURMENT= "00002a6f-0000-1000-8000-00805f9b34fb";
    public static String BATTERY_MEASURMENT = "00002a19-0000-1000-8000-00805f9b34fb";
    public static String SERIAL_NUMBER = "00002a25-0000-1000-8000-00805f9b34fb";
    public static String HARDWARE_NUMBER = "00002a27-0000-1000-8000-00805f9b34fb";
//    public static String DISTANCE_X = "000f7501-d102-11e1-9b23-00025b00a5a5";
//    public static String DISTANCE_Y = "000f7502-d102-11e1-9b23-00025b00a5a5";
    public static String DISTANCE_MEASUERMENT = "000f7503-d102-11e1-9b23-00025b00a5a5";


    static {
        // Sample Services.
        attributes.put("00001801-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("00001016-d102-11e1-9b23-00025b00a5a5", "Device Information Service");
        attributes.put("00001800-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "장치정보 서비스");
        attributes.put("0000181a-0000-1000-8000-00805f9b34fb", "온습도");
        attributes.put("0000180f-0000-1000-8000-00805f9b34fb", "배터리");
        attributes.put("000f7500-d102-11e1-9b23-00025b00a5a5", "이격거리");


        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");

        attributes.put(TEMPERATURE_MEASURMENT, "온도");
        attributes.put(HUMIDITY_MEASURMENT, "습도");
        attributes.put(BATTERY_MEASURMENT, "배터리 잔량");

        attributes.put(SERIAL_NUMBER, "시리얼 넘버");
        attributes.put(HARDWARE_NUMBER, "하드웨어 버전");
        attributes.put("00002a26-0000-1000-8000-00805f9b34fb", "펌웨어 버전");
        attributes.put("00002a28-0000-1000-8000-00805f9b34fb", "소프트웨어 버전");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "제조업자 이름");
        attributes.put("00002a50-0000-1000-8000-00805f9b34fb", "PnP ID");



        attributes.put(DISTANCE_MEASUERMENT, "z축 이격거리");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
