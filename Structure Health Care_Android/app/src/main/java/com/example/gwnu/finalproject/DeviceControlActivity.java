package com.example.gwnu.finalproject;

/**
 * Created by gwnu on 2016-12-02.
 */

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 인터페이스를 제공하고 데이터 표시
 연결된 장치에 있는 센서들 표시
 BluetoothLeService클래스와 번갈아 가며 수행되는 클래스 인듯?
 */
public class DeviceControlActivity extends Activity {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();//각각의 클래스들은 default tag를 갖는것같다.

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    //일반적인 안드로이드 프로그래밍시 다른 액티비티의 요소를 참조하려할땐
    //다른 액티비티의 것임을 표시하기 위해 extras라고 표기하는거 아닐까 추측

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    private String mDeviceName;
    private String mDeviceAddress;

    private TextView mConnectionState;
    private TextView mDataField;

    private boolean mConnected = false;

    private ExpandableListView mGattServicesList;//스캔결과물로 나온 연결가능 장치들
    private BluetoothLeService mBluetoothLeService;//연결된 장치, GATT 서버 라고하고
    // 장치에서 제공하는 여러가지 기능의 상위분류를 Gatt서비스라고 하는것 같다.
    // Gatt서비스의 종류로는 환경센서(해당센서가 있다면) 같은 센서들, 배터리 서비스, 장치정보 외 기타등등
    // 이 있으며 이 서비스들 하위로 Characteristic이라고 하는 세부적인 서비스가 있는데
    // 환경센서의 Characteristic은 온도, 습도, 배터리서비스의 Characteristic은 배터리 잔량 등이 있다.
    //Characteristic은 BluetoothGattCharacteristic등의 이름으로 길게부를때도 있고GattCharacteristic
    //라고 할때도 있으니 적당히 보자

    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();//?
    private BluetoothGattCharacteristic mNotifyCharacteristic;//?


    public final static UUID UUID_BATTERY_MEASURMENT =
            UUID.fromString(SampleGattAttributes.BATTERY_MEASURMENT);


    private UpdateThread updateThread = null;//new UpdateThread(this);
    static public float distance = -1;
    public int battery = 0;



    int distanceGroupPosition = 3;
    int distanceCharPosition = 2;

    int batteryGroupPosition = 4;
    int batteryCharPosition = 0;

    int temperatureGroupPosition = 4;
    int temperatureCharPosition = 0;

    int humidityGroupPostion = 4;
    int humidityCharPosition = 1;
    // Code to manage Service lifecycle.
    //ServiceConnection 은 서비스와의 연결상태를 모니터링하는데 쓰인다고 하는데 아직까지 서비스가 정확히 뭘말하는건지 모르겠다...
    //서비스가 장치를 말하는건가? 장치와 연결 선택시 onServiceConnected함수가 실행된다...
    //추상클래스 이므로 내부 함수 구현해줘야 한다.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {


        //연결하고싶은 장치 선택시 실행
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
            Log.e(TAG, "onServiceConnected함수 실행");

            updateThread = new UpdateThread(mBluetoothLeService);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };



    //안드로이드에서 제공하는건지 자바에서 제공하는건지는 모르겠는데 옵저버 클래스를 제공한다.
    //그게 BroadcastReceiver 이고
    //intent클래스는 다른 액티비티를 참조하는 클래스.
    //여기서는 통지를 주는 역할을 하겠지
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //인텐트 클래스의 getAction함수는 해당 액티비티가 어떠한 동작을 수행했는지를 문자열로 넘긴다.
            //우리는 아래와 같은것들만 체크한다.
            // ACTION_GATT_CONNECTED: GATT서버에 연결
            // ACTION_GATT_DISCONNECTED: GATT서버에 연결해지
            // ACTION_GATT_SERVICES_DISCOVERED: GATT서비스에 발견
            // ACTION_DATA_AVAILABLE: 장치로부터 데이터 수신
            //gatt가 아직 정확히 뭔지 모르겠음...

            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);//ui쓰레드를 이용하여 ui에 연결상태를 업데이트 해준다.
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                //           updateConnectionState(R.string.searching_service);
                displayGattServices(mBluetoothLeService.getSupportedGattServices());

                if(MainActivity.deviceName.equals("CSR Env Sensor") == true ) {
                    mBluetoothLeService.mHumidityCharacteristics = mGattCharacteristics.get(humidityGroupPostion).get(humidityCharPosition);
                    mBluetoothLeService.mTemperatureCharacteristics = mGattCharacteristics.get(temperatureGroupPosition).get(temperatureCharPosition);
                }
                else if(MainActivity.deviceName.equals("HAT-C1") == true)
                {
                    mBluetoothLeService.mDistanceCharacteristics = mGattCharacteristics.get(distanceGroupPosition).get(distanceCharPosition);
                }
                //쓰레드 시작
                updateThread.start();

                //  testFunc(5, 0);

                pass();
            }

        }
    };


    //주어진 GATT 특성을 선택하면  지원되는 기능을 확인합니다.
    //이 샘플은 '읽기'와 기능을 '알림 서비스'을 보여줍니다.
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html 전체에 대한
    // 지원되는 특성 기능의 목록입니다.

    //아마 gatt가 센서들을 말하는것 같다.
    //gatt센서들을 선택하였을때의 이벤트 처리 정의
    private final ExpandableListView.OnChildClickListener servicesListClickListner =
            new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                            int childPosition, long id) {

                    if (mGattCharacteristics != null) {
                        final BluetoothGattCharacteristic characteristic =
                                mGattCharacteristics.get(groupPosition).get(childPosition);
                        final int charaProp = characteristic.getProperties();

                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                            // If there is an active notification on a characteristic, clear
                            // it first so it doesn't update the data field on the user interface.
                            if (mNotifyCharacteristic != null) {
                                mBluetoothLeService.setCharacteristicNotification(
                                        mNotifyCharacteristic, false);
                                mNotifyCharacteristic = null;
                            }
                            mBluetoothLeService.readCharacteristic(characteristic);
                        }

                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            mNotifyCharacteristic = characteristic;
                            mBluetoothLeService.setCharacteristicNotification(
                                    characteristic, true);
                        }
                        return true;
                    }
                    return false;
                }
            };


    private void clearUI() {
        mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        mDataField.setText(R.string.no_data);
    }



    @Override
    //onCreate함수 실행전 변수 먼저 생성되고 내부가 따로 구현된 변수들의 경우 어떤게 구현되나 확인
    //bundleㅇ은 이전액티비티, 만약 이전액티비티 없으면 null이겠지
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gatt_services_characteristics);

        final Intent intent = getIntent();
//        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
  //      mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        mDeviceName = MainActivity.deviceName;
        mDeviceAddress = MainActivity.deviceAddress;



        // Sets up UI references.
        //리스트로부터 가져오면 mConnectionState에 대한 처리가 여러번 있어야 되는거 아닌가?
        ((TextView) findViewById(R.id.device_address)).setText(mDeviceAddress);
        mGattServicesList = (ExpandableListView) findViewById(R.id.gatt_services_list);
        mGattServicesList.setOnChildClickListener(servicesListClickListner);
        mConnectionState = (TextView) findViewById(R.id.connection_state);
        mDataField = (TextView) findViewById(R.id.data_value);


        boolean isNormal = false;
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        isNormal = bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);//
        System.out.println(isNormal);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }


    public void pass()
    {
        Intent mainIntent = new Intent(this, MainActivity.class);

        startActivity(mainIntent);
    }
    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(mGattUpdateReceiver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(mServiceConnection);
//        mBluetoothLeService = null;
//        updateThread.interrupt();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }

    private void displayData(String data) {
        if (data != null) {
            mDataField.setText(data);
        }
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    //
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;


        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);


        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.

        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();

            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);//이름과 uuid를 키값으로 해서 리스트에 저장
            gattServiceData.add(currentServiceData);//n번째 서비스의 이름과 uuid를 저장

            //!!!!!!!!!!!!!!!!!!!!!!!!/////////////
            // if(

            //!!!!!!!!!!!!!!!!!!!!!!!!/////////////


            //서비스들의 특성들의 목록에 이름과 uuid목록 저장
            //gattService에서 지원되는 특성들을 gattCharacteristics로 받아와 charas로 옮겨 저장
            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);//서비스에서 지원하는 서비스의 목록을 반환하는 함수의 반환 형태가 리스트인데
                // 배열기반에 저장하려고 charas에 별도로 보관



                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);

                gattCharacteristicGroupData.add(currentCharaData);
            }

            mGattCharacteristics.add(charas);//n번째 서비스가 제공할수 있는 특성들의 리스트를 추가했다.
            gattCharacteristicData.add(gattCharacteristicGroupData);//n번째 서비스가 제공할수 있는
            //특성들의 이름과 UUID를 가진 리스트를 요소로 갖는 리스트
        }

        SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
                this,
                gattServiceData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 },
                gattCharacteristicData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 }
        );
        mGattServicesList.setAdapter(gattServiceAdapter);
    }


    //옵저버로부터 받을 이벤트 등록
    private static IntentFilter makeGattUpdateIntentFilter() {

        final IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        return intentFilter;
    }
}