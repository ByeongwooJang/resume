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

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */

//디바이스가 스캔활동을 할때에 대한 정의가 담겨있는 클래스이다.

public class DeviceScanActivity extends ListActivity {
    private LeDeviceListAdapter mLeDeviceListAdapter;//디바이스 리스트를 표시하는데 어댑터가 필요한가?
                                                    //->안드로이드에서 제공하는게 아니라 내부클래스로
                                                    //직접 정의되어 있으니 밑에서 확인
                                                    //...확인해보면 결국 deviceid를 키로 갖는
                                                    //리스트의 랩퍼클래스 정도이다.

    private BluetoothAdapter mBluetoothAdapter;

    private boolean mScanning;
    private Handler mHandler;//뭐에대한 핸들러이지?

    private static final int REQUEST_ENABLE_BT = 1;
    private static final long SCAN_PERIOD = 10000;

    @Override
    //bundle은 이전 액티비티의 상태를 가지고 있는다.
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActionBar().setTitle(R.string.title_devices);//타이틀바에 표시되는 문자열을 설정하는건데...이 클래스는
                                                //스캔활동에 관한 클래스인데도 타이틀을 설정하네?
        mHandler = new Handler();

        //기기가 ble를 지원하는지 체크`
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        //블루트스 어뎁터 초기화.(API 18 , 그 이상을 위해), 블루트스 메니저를 토해 어뎁터를 참조
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        //기기가 블루투스를 지원하는지 체크`
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }


    //스캔이 시작되고 끝날떄
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);//xml을 이용하여 옵션을 만든다.
                                            //첫번째 인자는 해당 메뉴를 디자인한 xml
                                            //두번쨰 인자는 받아들여라

        if (!mScanning) {
            //main.xml에서 id에 해당하는 오브젝트를 찾아서 어떠한 동작을 수행한다.
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);


//            ArrayList<BluetoothDevice> listDevice = mLeDeviceListAdapter.mLeDevices;
//            int iSize = listDevice.size();
//
//            for(int i = 0; i < iSize; ++i)
//            {
//                BluetoothDevice device = listDevice.get(i);
//
//                String deviceName = device.getName();
//
//                if(deviceName.equals("HAT-C1") == true)
//                {
//                    AutoSelectConnect(i);
//                    break;
//                }
//            }

        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//사용자가 터치한 메뉴가 인자로 넘어온다.
        switch (item.getItemId()) {//넘어온 인자를 체크해서 알맞는 동작 수행
            case R.id.menu_scan:
                mLeDeviceListAdapter.clear();//이전에 찾은 디바이스 목록 초기화
                scanLeDevice(true);
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {//절전모드에서 깨거나 다른프로그램으로 인해 연결이 끊겼을떄 되돌아오도록 한다.
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        //비활성화일 경우 활성화 요청
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();
        setListAdapter(mLeDeviceListAdapter);
        scanLeDevice(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {//디바이스가 절전모드로 전환되거나 앱이 종료될때 실행
        super.onPause();
        scanLeDevice(false);
        mLeDeviceListAdapter.clear();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
        if (device == null) return;
        final Intent intent = new Intent(this, DeviceControlActivity.class);
//        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
 //       intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());

        MainActivity.deviceName = new String(device.getName());
        MainActivity.deviceAddress = new String(device.getAddress());

        if (mScanning) {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mScanning = false;
        }
        startActivity(intent);
    }


    //스캔이 시작되고 끝날때
    private void scanLeDevice(final boolean enable) {//기기가 매개변수에 따라 스캔을 수행 및 정지한다.

        if (enable) {
            // Stops scanning after a pre-defined scan period.

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {//빠르게 반복되는데 스캔시작 과 중지 사이에 쓰레드? 혹은 실행흐름이 엉키지 않도록
                                        //딜레이를 주는것 같다.
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);


        }
        invalidateOptionsMenu();
    }

    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;//플래터의 역할은 어떠한 레이아웃을 가리키는 포인터 정도?
                                        //아래 생성자에서 확인하자

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = DeviceScanActivity.this.getLayoutInflater();
            //getLayoutInflater함수를 컨트롤+b 누르고 타고 들어가면
            //상위 activity클래스의 함수로 들어가는데 내용을 살펴보면 어떠한 윈도우의 포커스를 가져오는,
            //레이아웃을 가져오는 함수정도로 이해할수 있을것 같다.
        }

        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
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
                //listitem_device레이아웃을 가져온다.

                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);//첫번째 파인드된 정보를 채워서 viewHolder로 넣는다.
            } else {
                viewHolder = (ViewHolder) view.getTag();//음...없어도 될거같긴 한데...
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

    // Device scan callback.
    //스캔 결과를 반환하는 콜백함수 구현...변수처럼 만들어서 쓰고있는데 이부분은 잘 모르겠다. 검색해도 잘 안나ㅗㅁ
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLeDeviceListAdapter.addDevice(device);
                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }
}