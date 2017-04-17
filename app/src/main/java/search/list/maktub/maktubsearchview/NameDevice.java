package search.list.maktub.maktubsearchview;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by dinhdv on 4/17/2017.
 */

public class NameDevice extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Device", "Values :" + getDeviceName() + "-- Phone  : " + getPhoneName() + "--  WifiName :" + getWifiName(NameDevice.this) + "-- Total : " + "MODEL: " + android.os.Build.MODEL
                        + "\nDEVICE: " + android.os.Build.DEVICE
                        + "\nBRAND: " + android.os.Build.BRAND
                        + "\nDISPLAY: " + android.os.Build.DISPLAY
                        + "\nBOARD: " + android.os.Build.BOARD
                        + "\nHOST: " + android.os.Build.HOST
                        + "\nMANUFACTURER: " + android.os.Build.MANUFACTURER
                        + "\nPRODUCT: " + android.os.Build.PRODUCT);

                BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
                Log.e("MainActivity", "bluethooth device_name: " + myDevice.getName());

                Log.e("MainActivity", "Build.MODEL: " + android.os.Build.MODEL);
            }
        });
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public String getWifiName(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    return wifiInfo.getSSID();
                }
            }
        }
        return null;
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public String getPhoneName() {
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName = myDevice.getName();
        return deviceName;
    }
}
