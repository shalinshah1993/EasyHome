package com.cepal.easyhome;

import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class delete1 extends Activity implements View.OnClickListener {
	BroadcastReceiver receiver;
	Button OnWifi, OffWifi, scan;
	TextView WifiState;
	WifiManager wifi;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;
	SharedPreferences someData;
	public static String image_path = "MySharedString";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete1);

		wifi = (WifiManager) getBaseContext().getSystemService(
				Context.WIFI_SERVICE);

		if (wifi == null) {
			Toast.makeText(this, "Wi-fi is not available", Toast.LENGTH_LONG)
					.show();
			finish();
			return;
		}
		setvars();
		setlisteners();
		this.registerReceiver(this.WifiStateChangedReceiver, new IntentFilter(
				WifiManager.WIFI_STATE_CHANGED_ACTION));
		// wifi.setWifiEnabled(true);

		if (wifi.isWifiEnabled()) {
			if (last_available()) {
				Log.d("dhaval", "last is availble");
				last_connect();
			} else {
				Log.d("dhaval", "last is not availble");
			}
		}
		else
		{
			wifi.setWifiEnabled(true);
			while(true)
			{
				if(wifi.getWifiState()==3)
					break;
			}
			Log.d("dhaval", "state ="+wifi.getWifiState());
			if (last_available()) {
				Log.d("dhaval", "last is availble");
				last_connect();
			} else {
				Log.d("dhaval", "last is not availble");
			}
		}
	}

	private void last_connect() {

		someData = getSharedPreferences(image_path, 0);
		String networkSSID = someData.getString("rem1", "no_string");
		WifiConfiguration conf = new WifiConfiguration();
		conf.BSSID = "\"" + networkSSID + "\"";

		WifiManager wifi3 = (WifiManager) getBaseContext().getSystemService(
				Context.WIFI_SERVICE);
		// WifiManager wifiManager =
		// (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		wifi3.startScan();

		while (wifi3.SCAN_RESULTS_AVAILABLE_ACTION == null)
			try {
				wifi3.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// mNewDevicesArrayAdapter =
		// (ArrayAdapter<String>)wifi2.getScanResults();
		// Toast.makeText(this, text, duration)
		// Log.d("Yo man", "came in update");

		// wifi3.addNetwork(conf);
		List<WifiConfiguration> list = wifi3.getConfiguredNetworks();
		for (WifiConfiguration i : list) {
			Log.d("sad man ", "in1" + i.BSSID);
			Log.d("sad man ", "in1" + i.SSID);
			if (i.SSID != null && i.SSID.equals(networkSSID)) {
				Log.d("sad man ", "in2");
				wifi3.disconnect();
				boolean var2 = wifi3.enableNetwork(i.networkId, true);

				boolean var = wifi3.reconnect();

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String var3 = wifi3.getConnectionInfo().getSSID();
				String var4 = wifi3.getConnectionInfo().getBSSID();

				if (var3 == null) {
					Log.d("dhaval", "var3=  " + var3);
					return;
				}

				Log.d("dhaval", "trying " + i.SSID);
				Log.d("dhaval", "var=  " + var);
				Log.d("dhaval", "var2=  " + var2);
				Log.d("dhaval", "var3=  " + var3);

				if (var) {
					Intent intent1 = new Intent("com.cepal.easyhome.HOME_MENU");
					delete3.setconn();
					Log.d("dhaval", "connected wifi");
					startActivity(intent1);
					finish();
					break;
				}
			}
		}
	}

	private boolean last_available() {
		someData = getSharedPreferences(image_path, 0);
		String last = someData.getString("rem1", "no_string");
		Log.d("dhaval", last);
		WifiManager wifi3 = (WifiManager) getBaseContext().getSystemService(
				Context.WIFI_SERVICE);
		// WifiManager wifiManager =
		// (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		// wifi3.addNetwork(conf);

		List<WifiConfiguration> list = wifi3.getConfiguredNetworks();
		for (WifiConfiguration i : list) {
			// wifiDemo.WifiState.setText(result+"\n");
			if (i.SSID.toString().equals(last)) {
				Log.d("dhaval", "checking with " + i.SSID.toString());
				Log.d("dhaval", "same");
				return true;
			}

			// mNewDevicesArrayAdapter.add(ans);
		}
		return false;

	}

	private void setlisteners() {
		// TODO Auto-generated method stub
		OnWifi.setOnClickListener(this);
		OffWifi.setOnClickListener(this);
		scan.setOnClickListener(this);

	}

	private void setvars() {
		WifiState = (TextView) findViewById(R.id.wifistate);
		OnWifi = (Button) findViewById(R.id.onwifi);
		OffWifi = (Button) findViewById(R.id.offwifi);
		scan = (Button) findViewById(R.id.scan);

	}

	private BroadcastReceiver WifiStateChangedReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			int extraWifiState = intent.getIntExtra(
					WifiManager.EXTRA_WIFI_STATE,
					WifiManager.WIFI_STATE_UNKNOWN);

			switch (extraWifiState) {
			case WifiManager.WIFI_STATE_DISABLED:
				WifiState.setText("WIFI STATE DISABLED");
				break;
			case WifiManager.WIFI_STATE_DISABLING:
				WifiState.setText("WIFI STATE DISABLING");
				break;
			case WifiManager.WIFI_STATE_ENABLED:
				WifiState.setText("WIFI STATE ENABLED");
				break;
			case WifiManager.WIFI_STATE_ENABLING:
				WifiState.setText("WIFI STATE ENABLING");
				break;
			case WifiManager.WIFI_STATE_UNKNOWN:
				WifiState.setText("WIFI STATE UNKNOWN");
				break;
			}

		}
	};

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.onwifi:

			wifi.setWifiEnabled(true);
			// WifiInfo info = wifi.getConnectionInfo();
			break;
		case R.id.offwifi:
			wifi.setWifiEnabled(false);
			// WifiInfo info = wifi.getConnectionInfo();

			break;
		case R.id.scan:
			if (wifi.isWifiEnabled()) {
				Intent n = new Intent("com.cepal.easyhome.CHOOSE3");
				startActivity(n);
				finish();
			} else {
				Toast.makeText(this, "Please Enable First ! ",
						Toast.LENGTH_SHORT).show();
				Log.d("sad", "first connecet re man ! ");
			}
			// scanforme();

		}

	}

	private void scanforme() {

		List<ScanResult> results = wifi.getScanResults();
		if (results != null) {
			for (ScanResult result : results) {
				WifiState.setText(result + "\n");
			}
		} else {
			WifiState.setText("No connection available ! " + "\n");
		}
	}

	public void onStop() {
		super.onStop();
		if (receiver != null)
			unregisterReceiver(receiver);
	}
}