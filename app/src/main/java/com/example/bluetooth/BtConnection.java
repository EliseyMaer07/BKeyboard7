package com.example.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.adapter.BtAdapter;
import com.example.adapter.BtConsts;

import java.nio.charset.StandardCharsets;

public class BtConnection {
    private Context context;
    private SharedPreferences pref;
    private BluetoothAdapter btAdapter;
    private BluetoothDevice device;
    private ConnectThread connectThread;
    private int con = 0;
    //private ConnectThread tConnect;

    public BtConnection(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(BtConsts.MY_PREF, Context.MODE_PRIVATE);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }
    public void conect(){
        String mac = pref.getString(BtConsts.MAC_KEY, "");
        if(!btAdapter.isEnabled() || mac.isEmpty()) return;
        device = btAdapter.getRemoteDevice(mac);
        if(device == null) return;
        connectThread = new ConnectThread(context, btAdapter, device);
        //connectThread.gettConnect();
        connectThread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        con = connectThread.getConnect();
        System.out.println(con + "!!!!!!!!!!!!!!!!!!!!!");
    }
    public void sendMessage(String message){
        connectThread.getRThread().sendMessage(message.getBytes());
    }

    public int getCon() {
        return con;
    }
}
