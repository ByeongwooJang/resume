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
        import android.app.Service;
        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.bluetooth.BluetoothGatt;
        import android.bluetooth.BluetoothGattCallback;
        import android.bluetooth.BluetoothGattCharacteristic;
        import android.bluetooth.BluetoothGattDescriptor;
        import android.bluetooth.BluetoothGattService;
        import android.bluetooth.BluetoothManager;
        import android.bluetooth.BluetoothProfile;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.os.Binder;
        import android.os.IBinder;
        import android.util.Log;

        import java.util.List;
        import java.util.UUID;

/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
public class BluetoothLeService extends Service {
    private final static String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;

    public BluetoothGattCharacteristic mDistanceCharacteristics;
    public BluetoothGattCharacteristic mGasCharacteristics;
    public BluetoothGattCharacteristic mTemperatureCharacteristics;
    public BluetoothGattCharacteristic mHumidityCharacteristics;
    public BluetoothGattCharacteristic mNotifyCharacteristic;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";

    public final static UUID UUID_HEART_RATE_MEASUREMENT =
            UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);

    public final static UUID UUID_TEMPERATURE_MEASURMENT =
            UUID.fromString(SampleGattAttributes.TEMPERATURE_MEASURMENT);
    public final static UUID UUID_HUMIDITY_MEASURMENT =
            UUID.fromString(SampleGattAttributes.HUMIDITY_MEASURMENT);
    public final static UUID UUID_BATTERY_MEASURMENT =
            UUID.fromString(SampleGattAttributes.BATTERY_MEASURMENT);
    public final static UUID UUID_SERIAL_NUMBER =
            UUID.fromString(SampleGattAttributes.SERIAL_NUMBER);
    public final static UUID UUID_HARDWARE_NUMBER =
            UUID.fromString(SampleGattAttributes.HARDWARE_NUMBER);

    public final static UUID UUID_DISTANCE_MEASUERMENT =
            UUID.fromString(SampleGattAttributes.DISTANCE_MEASUERMENT);


    CalculateDistance distancecalculater = new CalculateDistance();

    public boolean isUpdate = false;
    public int updateCounter = 0;

    public static double distance = -1;
    public static double gas = -1;
    public static double temperature = -1;
    public static double humidity = -1;


    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    //if there is some changes, it perform

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);//brodcast what actions doing for which
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());



            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };


    public void setTemperatureCharacter(BluetoothGattCharacteristic temperature)
    {
        mTemperatureCharacteristics = temperature;
    }

    public void setHumidityCharacter(BluetoothGattCharacteristic humidity)
    {
        mHumidityCharacteristics = humidity;
    }

    public boolean reqTemperature(int groupPosition, int childPosition) {

        if (mTemperatureCharacteristics != null) {
            final BluetoothGattCharacteristic characteristic = mTemperatureCharacteristics;

            final int charaProp = characteristic.getProperties();

            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
                if (mNotifyCharacteristic != null) {
                    setCharacteristicNotification(
                            mNotifyCharacteristic, false);
                    mNotifyCharacteristic = null;
                }
                readCharacteristic(characteristic);
            }

            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                mNotifyCharacteristic = characteristic;
                setCharacteristicNotification(
                        characteristic, true);
            }

            return true;
        }


        return false;

    }


    public boolean reqHumidity(int groupPosition, int childPosition) {

        if (mHumidityCharacteristics != null) {
            final BluetoothGattCharacteristic characteristic = mHumidityCharacteristics;

            final int charaProp = characteristic.getProperties();

            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
                if (mNotifyCharacteristic != null) {
                    setCharacteristicNotification(
                            mNotifyCharacteristic, false);
                    mNotifyCharacteristic = null;
                }
                readCharacteristic(characteristic);
            }

            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                mNotifyCharacteristic = characteristic;
                setCharacteristicNotification(
                        characteristic, true);
            }
            return true;
        }
        return false;
    }

    public boolean reqDistance(int groupPosition, int childPosition) {

        if (mDistanceCharacteristics != null) {
            final BluetoothGattCharacteristic characteristic = mDistanceCharacteristics;

            final int charaProp = characteristic.getProperties();

            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
                if (mNotifyCharacteristic != null) {
                    setCharacteristicNotification(
                            mNotifyCharacteristic, false);
                    mNotifyCharacteristic = null;
                }
                readCharacteristic(characteristic);
            }

            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                mNotifyCharacteristic = characteristic;
                setCharacteristicNotification(
                        characteristic, true);
            }
            return true;
        }
        return false;
    }

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //인텐트 클래스의 getAction함수는 해당 액티비티가 어떠한 동작을 수행했는지를 문자열로 넘긴다.
            //->getAction is returning about string array that which activiy was waht did
            // ACTION_GATT_CONNECTED: GATT서버에 연결
            // ACTION_GATT_DISCONNECTED: GATT서버에 연결해지
            // ACTION_GATT_SERVICES_DISCOVERED: GATT서비스에 발견
            // ACTION_DATA_AVAILABLE: 장치로부터 데이터 수신
            

            if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {

                if(MainActivity.deviceName.equals("CSR Env Sensor") == true) {
                    if (updateCounter == 0)//temperature
                    {
                        String data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                        temperature = Float.parseFloat(data);

                        MainActivity.etTem.setText(new String(String.format("%.1f", temperature)));
                        MainActivity.etGas.setText(String.valueOf(77));

                        System.out.println("온도 : " + temperature);
                    } else if (updateCounter == 1)//humidity
                    {
                        String data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                        humidity = Float.parseFloat(data);

                        MainActivity.etHum.setText(new String(String.format("%.1f", humidity)));
                        MainActivity.etDis.setText(String.valueOf(44.4));

                        System.out.println("습도 : " + humidity);
                    }
                    ++updateCounter;
                    updateCounter %= 2;
                    isUpdate = true;
                    System.out.println("여기선 업데이트 바꿈 " + isUpdate);

                }
                else if(MainActivity.deviceName.equals("HAT-C1") == true)
                {
                    if (updateCounter == 0)//distance
                    {
                        String data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                        distance = Float.parseFloat(data);

                        MainActivity.etDis.setText(new String(String.format("%.1f", distance)));

                        MainActivity.etGas.setText(String.valueOf(77));
                        MainActivity.etHum.setText(String.valueOf(40));
                        MainActivity.etTem.setText(String.valueOf(23));


                        System.out.println("온도 : " + temperature);
                    }

                    ++updateCounter;
                    updateCounter %= 1;
                    isUpdate = true;

                    System.out.println("여기선 업데이트 바꿈 " + isUpdate);
                }


            }
        }
    };

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);

        // This is special handling for the Heart Rate Measurement profile.  Data parsing is
        // carried out as per profile specifications:
        // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
        if(UUID_TEMPERATURE_MEASURMENT.equals(characteristic.getUuid()))
        {
            byte[] arrTemperature = characteristic.getValue();
            float rTemperature = (arrTemperature[0] & 0xff) | (arrTemperature[1] << 8);
            rTemperature /= 100.f;
//            intent.putExtra(EXTRA_DATA, new String(String.format("%.2f" + "℃", rTemperature)));
            intent.putExtra(EXTRA_DATA, new String(String.format("%.1f", rTemperature)));

        }
        else if(UUID_HUMIDITY_MEASURMENT.equals(characteristic.getUuid()))
        {
            byte[] arrHumidity = characteristic.getValue();
            float rHumidity = (arrHumidity[0] & 0xff) | (arrHumidity[1] << 8);
            rHumidity /= 100.f;

            String szHumidity = new String(String.format("%.1f", rHumidity));
//            szHumidity += "%";
            intent.putExtra(EXTRA_DATA, szHumidity);
        }
        else if(UUID_BATTERY_MEASURMENT.equals(characteristic.getUuid()))
        {
            byte[] arrBattery = characteristic.getValue();
            if (arrBattery != null && arrBattery.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(arrBattery.length);
                for (byte byteChar : arrBattery)
                    stringBuilder.append(String.format("%d ", byteChar));
//                intent.putExtra(EXTRA_DATA, stringBuilder.toString() + "%");
                intent.putExtra(EXTRA_DATA, stringBuilder.toString());

            }
        }
        else if(UUID_DISTANCE_MEASUERMENT.equals(characteristic.getUuid()))
        {
            byte[] arrDistance = characteristic.getValue();
            double dDistanceZ = (arrDistance[4] & 0xff) | (arrDistance[5] << 8);

            double transdata = distancecalculater.getTmagnetInterpolationZ(dDistanceZ);
//            intent.putExtra(EXTRA_DATA, new String(String.format("%.2fmm", transdata)));
            intent.putExtra(EXTRA_DATA, new String(String.format("%.2f", transdata)));


        }
        else {
            // For all other profiles, writes the data formatted in HEX.
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                for(byte byteChar : data)
                    stringBuilder.append(String.format("%02X ", byteChar));
                intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
            }
        }
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();



//    public BluetoothLeService()
//    {
//        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
//    }


    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result
     *         is reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic CharacterBluetoothLeServiceistic to act on.
     * @param enabled If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

        // This is specific to Heart Rate Measurement.
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
    }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }

    private static IntentFilter makeGattUpdateIntentFilter() {

        final IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

}
