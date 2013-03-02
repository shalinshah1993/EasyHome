package com.cepal.easyhome;

/*
 * Copyright (C) 2009 The Android Open Source Project
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

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * This Activity appears as a dialog. It lists any paired devices and devices
 * detected in the area after discovery. When a device is chosen by the user,
 * the MAC address of the device is sent back to the parent Activity in the
 * result Intent.
 */
public class DeviceListActivity2 extends Activity {
	// Debugging
	private static final String TAG = "DeviceListActivity";
	private static final boolean D = true;

	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";

	// Member fields
	// private BluetoothAdapter mBtAdapter;
	// private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;
	SharedPreferences someData;
	static String image_path = "MySharedString";
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setup the window
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.device_list);

		// Set result CANCELED incase the user backs out
		setResult(Activity.RESULT_CANCELED);

		// Initialize the button to perform device discovery
		Button scanButton = (Button) findViewById(R.id.button_scan);
		scanButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// doDiscovery();
				// v.setVisibility(View.GONE);
				Log.d("scan", "scan button clicked ! ");
				update_array2();
			}
		});

		// Initialize array adapters. One for already paired devices and
		// one for newly discovered devices
		// mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
		// R.layout.device_name);
		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);

		// Find and set up the ListView for paired devices
		// ListView pairedListView = (ListView)
		// findViewById(R.id.paired_devices);
		// pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		// pairedListView.setOnItemClickListener(mDeviceClickListener);

		// Find and set up the ListView for newly discovered devices
		ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
		update_array2();
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);

	}

	private void update_array2() {
		WifiManager wifi2 = (WifiManager) getBaseContext().getSystemService(
				Context.WIFI_SERVICE);
		wifi2.startScan();

		while (wifi2.SCAN_RESULTS_AVAILABLE_ACTION == null)
			try {
				wifi2.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// mNewDevicesArrayAdapter =
		// (ArrayAdapter<String>)wifi2.getScanResults();
		// Toast.makeText(this, text, duration)
		Log.d("Yo man", "came in update");
		List<ScanResult> results = wifi2.getScanResults();
		//ScanResult bestSignal = null;
		mNewDevicesArrayAdapter.clear();
		for (ScanResult result : results) {
			// wifiDemo.WifiState.setText(result+"\n");
			String ans = "Name : ";
			ans += result.SSID + "\n";
			ans += "BSSID : ";
			ans += result.BSSID + "\n";

			mNewDevicesArrayAdapter.add(ans);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Make sure we're not doing discovery anymore

		// Unregister broadcast listeners
		// this.unregisterReceiver(mReceiver);
	}

	/**
	 * Start device discover with the BluetoothAdapter
	 */
	private void doDiscovery() {

		// Indicate scanning in the title
		setProgressBarIndeterminateVisibility(true);
		setTitle(R.string.scanning);

		// Turn on sub-title for new devices
		findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);
	}

	// The on-click listener for all devices in the ListViews
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			// Cancel discovery because it's costly and we're about to connect
			// mBtAdapter.cancelDiscovery();

			// Get the device MAC address, which is the last 17 chars in the
			// View
			String info = ((TextView) v).getText().toString();
			int temp1 = info.indexOf("\n");
			String address = info.substring(7, temp1);

			String networkSSID = address;
			WifiConfiguration conf = new WifiConfiguration();
			conf.BSSID = "\"" + networkSSID + "\"";
			

			WifiManager wifi3 = (WifiManager) getBaseContext()
					.getSystemService(Context.WIFI_SERVICE);
			// WifiManager wifiManager =
			// (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			wifi3.addNetwork(conf);

			List<WifiConfiguration> list = wifi3.getConfiguredNetworks();
			for (WifiConfiguration i : list) {
				Log.d("sad man ", "in1" + i.BSSID);
				if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
					Log.d("sad man ", "in2");
					wifi3.disconnect();
					wifi3.enableNetwork(i.networkId, true);
					boolean var = wifi3.reconnect();
					Log.d("dhaval", "trying " + i.SSID);
					Log.d("dhaval", "var=  " + var);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (var) {
						Intent intent1 = new Intent(
								"com.cepal.easyhome.HOME_MENU");
						delete3.setconn();
						Log.d("dhaval", "connected wifi");

						someData = getSharedPreferences(image_path, 0);
						editor = someData.edit();						
						editor.putString("rem1", "\"" + networkSSID + "\"");
						editor.commit();
						startActivity(intent1);
						finish();
						break;
					} else {
						Log.d("dhaval", "can not connect wifi");
						Toast.makeText(DeviceListActivity2.this,
								"Please choose another network! ",
								Toast.LENGTH_LONG).show();

					}
				}
			}
			// Log.d("Yo man i am clicked ! :O ",info);
			Log.d("Yo man", address);
			Toast.makeText(DeviceListActivity2.this,
					"Please choose another network! ", Toast.LENGTH_LONG)
					.show();
			// Create the result Intent and include the MAC address
			// Intent intent = new Intent();
			// intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

			// Set result and finish this Activity
			// setResult(Activity.RESULT_OK, intent);
			// finish();
		}
	};

	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished

}
