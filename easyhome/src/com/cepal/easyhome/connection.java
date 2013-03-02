package com.cepal.easyhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class connection extends Activity {

	int idOfSelectedItem;
	int temp = 0;

	private static final String TAG = "BluetoothSendService";
	private static final boolean D = true;

	public String command;

	public String message;

	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	private static final int REQUEST_SPEECH = 3;

	public static final String DEVICE_NAME = "device_name";

	private String mConnectedDeviceName = null;
	Button b1;
	public static SharedPreferences sharedData;
	public static String image_path = "MySharedString";
	SharedPreferences.Editor editor;
	public static TextView tv1;
	String address;
	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	private BluetoothAdapter mBluetoothAdapter = null;
	// String buffer for outgoing messages
	public static StringBuffer mOutStringBuffer;

	public static BluetoothSendService mSendService = null;

	private static BluetoothDevice device = null;
	private static int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Toast.makeText(this, "On create", 2).show();
		setContentView(R.layout.temp_check);
		setvars();
		setlisteners();
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}
	}

	private void last_connect() {

		final String cone = sharedData.getString("last_connection", "temp");
		final String cone_name = sharedData.getString("last_connection_name",
				"temp");
		Log.d("dhaval", "in last_connect connecting " + cone);
		if (!cone.equals("temp")) {

			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent int1 = null;
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						// Yes button clicked
						Log.d("dhaval", "in last_connect connecting positive"
								+ cone);
						// setupSend();
						device = mBluetoothAdapter.getRemoteDevice(cone);
						// Attempt to connect to the device

						mSendService.connect(device);
						// dialog.dismiss();
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						// No button clicked
						break;
					}

				}
			};
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Do you want to make connection with '" + cone_name
							+ "' ? ")
					.setPositiveButton("Yes", dialogClickListener)
					.setNegativeButton("No", dialogClickListener).show();

		} else {
			tv1.setText("No device remembered ! ");
		}
	}

	private void setvars() {

		// b1 = (Button)findViewById(R.id.button1);
		tv1 = (TextView) findViewById(R.id.textView1);
		sharedData = getSharedPreferences(image_path, 0);
		editor = sharedData.edit();
	}

	private void setlisteners() {

		// b1.setOnClickListener(this);
	}

	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("dhaval", "came in on start SAD ! ");
		// Toast.makeText(this, "On start", 5);
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			ensureDiscoverable();
			// Otherwise, setup the chat session
		} else {
			if (mSendService == null)
				setupSend();

			else
				last_connect();

		}

	}

	public synchronized void onResume() {
		super.onResume();
		if (D)
			Log.e(TAG, "+ ON RESUME +");

		// Toast.makeText(this, "On resume", 5).show();

		// Button onButton = (Button) findViewById(R.id.On);
		// onButton.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		// Send a message using content of the edit text widget
		// TextView view = (TextView) findViewById(R.id.edit_text_out);
		// if(idOfSelectedItem == 0)
		// {
		// message = "MD1X4Y1N";
		// sendMessage(message);
		// }
		// }
		// });

	}

	private void setupSend() {
		// Log.d(TAG, "setupChat()");

		// Initialize the BluetoothChatService to perform bluetooth connections
		mSendService = new BluetoothSendService(this, mHandler);

		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
		last_connect();

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (D)
				Log.d(TAG, "onActivityResult " + resultCode);
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				tv1.setText("Making connection, Please Wait...");

				// new
				// AlertDialog.Builder(this).setTitle(address).setMessage("Watch out!").setNeutralButton("Close",
				// null).show();
				// Get the BLuetoothDevice object
				device = mBluetoothAdapter.getRemoteDevice(address);
				// Attempt to connect to the device

				mSendService.connect(device);

			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				setupSend();
			} else {
				// User did not enable Bluetooth or an error occured
				Log.d(TAG, "BT not enabled");
				// Toast.makeText(this, R.string.bt_not_enabled_leaving,
				// Toast.LENGTH_SHORT).show();
				finish();
			}

		}
	}

	private static void sendMessage(String message) {
		// Check that we're actually connected before trying anything

		if (mSendService == null) {
			Log.d(TAG, "bye");
			// finish();
		}

		if (mSendService.getState() != BluetoothSendService.STATE_CONNECTED) {
			// Toast.makeText(this, "Not connected!",
			// Toast.LENGTH_SHORT).show();
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			// new
			// AlertDialog.Builder(this).setTitle(message).setMessage("Watch out!").setNeutralButton("Close",
			// null).show();
			// if (D) Log.d(TAG, message + this);
			if (mSendService.write(send)) {
				Log.d(TAG, "Writes!!");

			} else {
				Log.d(TAG, "problem!!");

			}

			// Reset out string buffer to zero and clear the edit text field

			mOutStringBuffer.setLength(0);
			// mOutEditText.setText(mOutStringBuffer);
		}
	}

	private void ensureDiscoverable() {
		// if(D) Log.d(TAG, "ensure discoverable");
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 1800);
			startActivity(discoverableIntent);
		}
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				// if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothSendService.STATE_CONNECTED:
					tv1.setText("connected to...");
					tv1.append(mConnectedDeviceName);
					editor.putString("last_connection", address);
					editor.putString("last_connection_name",
							mConnectedDeviceName);
					editor.commit();

					// mConversationArrayAdapter.clear();
					break;
				case BluetoothSendService.STATE_CONNECTING:
					Log.d("dhaval", "connection state connecting");
					tv1.setText("Connecting...");
					break;
				// case BluetoothChatService.STATE_LISTEN:
				case BluetoothSendService.STATE_NONE:
					tv1.setText("Not connected");
					break;
				case BluetoothSendService.STATE_CAN_NOT_CONNECT:
					Log.d("dhaval", "came came came123");
					connection.tv1.setText("Please Retry... Can not connect! ");
					break;
				}
				break;
			case MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMessage = new String(writeBuf);

				// Toast.makeText(this,"hi",Toast.LENGTH_SHORT).show();
				if (D)
					Log.i(TAG, writeMessage);

				// console.log(writeMessage);
				// mConversationArrayAdapter.add("Me:  " + writeMessage);
				break;
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// construct a string from the valid bytes in the buffer
				String readMessage = new String(readBuf, 0, msg.arg1);
				if (D)
					Log.d(TAG, readMessage);
				Toast.makeText(getApplicationContext(), readMessage,
						Toast.LENGTH_SHORT).show();

				// mConversationArrayAdapter.add(mConnectedDeviceName+":  " +
				// readMessage);
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				editor.putString("connected_device", address);
				// tv1.setText("Connected to +"+address);
				editor.commit();
				tv1.setText(mConnectedDeviceName);
				Intent in = new Intent("com.cepal.easyhome.HOME_MENU");
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
				// finish();
				break;
			// case MESSAGE_TOAST:
			// Toast.makeText(getApplicationContext(),
			// msg.getData().getString(TOAST),
			// Toast.LENGTH_SHORT).show();
			// break;
			}

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater menuinflater = getMenuInflater();
		menuinflater.inflate(R.menu.menu_settings, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.scan:

			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			return true;

		case R.id.discoverable:
			ensureDiscoverable();

			return true;

		}
		return false;
	}

	
	
	public static void write_bt(String s) {
		
		sendMessage(s);
	}

	

}
