package com.cepal.easyhome;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class delete2 extends BroadcastReceiver {
  private static final String TAG = "WiFiScanReceiver";
  delete1 wifiDemo;

  public delete2(delete1 wifiDemo) {
    super();
    this.wifiDemo = wifiDemo;
  }

  @Override
  public void onReceive(Context c, Intent intent) {
	  Log.d(TAG, "came in rcv" );
	  List<ScanResult> results = wifiDemo.wifi.getScanResults();
    ScanResult bestSignal = null;
    for (ScanResult result : results) {
      wifiDemo.WifiState.setText(result+"\n");
    }
    Log.d(TAG, "onReceive() message: we did it ! :D " );
  }

}