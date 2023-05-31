package com.example.bluetooth;

import android.Manifest;
import android.app.LauncherActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.BtListActivity;
import com.example.MainActivity;
import com.example.adapter.BtAdapter;
import com.example.adapter.BtConsts;

import java.io.IOException;

public class ConnectThread extends Thread {
    private Context context;
    private BluetoothAdapter btAdapter;
    private BluetoothDevice device;
    private BluetoothSocket mSocket;
    private ReciveThread rThread;
    private int Connect = 0;
    public static final String UUID = "00001101-0000-1000-8000-00805F9B34FB";

    public ConnectThread(Context context, BluetoothAdapter btAdapter, BluetoothDevice device) {
        this.context = context;
        this.btAdapter = btAdapter;
        this.device = device;
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mSocket = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));
        } catch (IOException e) {

        }
    }

    @Override
    public void run() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        btAdapter.cancelDiscovery();
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mSocket.connect();
            rThread = new ReciveThread(mSocket);
            rThread.start();
            Connect = 1;
            System.out.println("Connected///////////////////////////////////////////////////////" + Connect);
            //Toast.makeText(context, "Подоключено", Toast.LENGTH_SHORT).show();
            //System.out.println("1");
        } catch (IOException e){
            Connect = 0;
            System.out.println("Not Connected///////////////////////////////////////////////////" + Connect);
            //Toast.makeText(context, "Не подоключено", Toast.LENGTH_SHORT).show();
            closeConnection();
            //System.out.println("0");
        }
    }
    public void closeConnection(){
        try {
            mSocket.close();
        } catch (IOException y){

        }
    }

    public ReciveThread getRThread() {
        return rThread;
    }

    public int getConnect() {
        return Connect;
    }

    public void setConnect(int connect) {
        Connect = connect;
    }
}
