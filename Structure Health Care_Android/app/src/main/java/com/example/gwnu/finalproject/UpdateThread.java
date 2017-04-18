package com.example.gwnu.finalproject;

import android.content.ServiceConnection;

/**
 * Created by Ryong on 2016-11-20.
 */

public class UpdateThread extends Thread {

//    int distanceGroupPosition = 3;
//    int distanceCharPosition = 2;
//
//    int batteryGroupPosition = 4;
//    int batteryCharPosition = 0;
//
//    int temperatureGroupPosition = 4;
//    int temperatureCharPosition = 0;
//
//    int humidityGroupPostion = 4;
//    int humidityCharPosition = 1;

    private BluetoothLeService bleService = null;

    public UpdateThread( BluetoothLeService bleService)
    {
        this.bleService = bleService;
    }

    public void run()
    {
        while(true) {
            try {

                bleService.isUpdate = false;
                System.out.println("update " + bleService.updateCounter + "번");

                if(MainActivity.deviceName.equals("CSR Env Sensor") == true) {
                    System.out.println("온습도 센서");
                    if (bleService.updateCounter == 0) {
                        bleService.reqTemperature(0, 0);
                    } else if (bleService.updateCounter == 1) {
                        bleService.reqHumidity(0, 0);
                    }
                }
                else if(MainActivity.deviceName.equals("HAT-C1") == true)
                {
                    System.out.println("거리 센서");
                    if (bleService.updateCounter == 0) {
                        bleService.reqDistance(0, 0);
                    }
                }
                else
                {
                    System.out.println("확인되지 않은 센서");
                }


                for(;bleService.isUpdate != true;) {
                    Thread.sleep(2000);
                    System.out.println("슬립중..업데이트는" + bleService.isUpdate);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
