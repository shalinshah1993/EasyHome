package com.cepal.easyhome;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.cepal.easyhome.connection;

public class tv extends Activity implements View.OnClickListener {

	ImageButton ib1, ib2, ib3, ib4, ib5,ib6;
	boolean toggle = false;

	// connection conn = new connection();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tv);
		setvars();
		setlisteners();
	}

	private void setvars() {

		ib1 = (ImageButton) findViewById(R.id.imageButton1);
		ib2 = (ImageButton) findViewById(R.id.imageButton2);
		ib3 = (ImageButton) findViewById(R.id.imageButton3);
		ib4 = (ImageButton) findViewById(R.id.imageButton4);
		ib5 = (ImageButton) findViewById(R.id.imageButton5);
		ib6 = (ImageButton) findViewById(R.id.imageButton6);
		ib6.setBackgroundColor(Color.WHITE);
		// ib1.setBackgroundColor(Color.GREEN);
		// ib2.setBackgroundColor(Color.BLUE);
		// ib3.setBackgroundColor(Color.BLUE);
		// ib4.setBackgroundColor(Color.BLUE);
		// ib5.setBackgroundColor(Color.BLUE);
		// b2 = (Button) findViewById(R.id.Button2);
		// tb = (ToggleButton) findViewById(R.id.toggleButton1);
	}

	private void setlisteners() {

		// tb.setOnClickListener(this);
		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		ib3.setOnClickListener(this);
		ib4.setOnClickListener(this);
		ib5.setOnClickListener(this);

	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.imageButton1:

			if (!toggle) {
				if(isconnection())
				{
					if(isbluetooth())
				connection.write_bt("MD5X4Y1N");
					else
					{
					WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					WifiInfo info = manager.getConnectionInfo();
					String address = info.getMacAddress();
					delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD5X4Y1N");
					}

				}
					//connection.write_bt("MD5X4Y1N");
				// ib1.setBackgroundColor(Color.GREEN);
				// ib1.setImageResource(R.drawable.on);
				ib6.setBackgroundColor(Color.YELLOW);
				Toast.makeText(this, "ON", Toast.LENGTH_SHORT).show();
				toggle = true;

			} else {
				// ib1.setBackgroundColor(Color.RED);
				// ib1.setImageResource(R.drawable.off);
				if(isconnection())
				{
					if(isbluetooth())
				connection.write_bt("MD5X4Y2N");
					else
					{
					WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					WifiInfo info = manager.getConnectionInfo();
					String address = info.getMacAddress();
					delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD5X4Y2N");
					}

				}
					//connection.write_bt("MD5X4Y2N");
				ib6.setBackgroundColor(Color.WHITE);
				Toast.makeText(this, "OFF", Toast.LENGTH_SHORT).show();
				toggle = false;
			}
			break;

		case R.id.imageButton2:
			if(isconnection())
			{
				if(isbluetooth())
			connection.write_bt("MD5X2Y1N");
				else
				{
				WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = manager.getConnectionInfo();
				String address = info.getMacAddress();
				delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD5X2Y1N");
				}

			}
				//connection.write_bt("MD5X2Y1N");
			Toast.makeText(this, "Decreasing volume", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.imageButton3:
			if(isconnection())
			{
				if(isbluetooth())
			connection.write_bt("MD5X6Y1N");
				else
				{
				WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = manager.getConnectionInfo();
				String address = info.getMacAddress();
				delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD5X6Y1N");
				}

			}
				//connection.write_bt("MD5X6Y1N");

			Toast.makeText(this, "Increasing volume", Toast.LENGTH_SHORT)
					.show();
			break;

		case R.id.imageButton4:
			if(isconnection())
			{
				if(isbluetooth())
			connection.write_bt("MD5X3Y1N");
				else
				{
				WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = manager.getConnectionInfo();
				String address = info.getMacAddress();
				delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD5X3Y1N");
				}

			}
				//connection.write_bt("MD5X3Y1N");

			Toast.makeText(this, "Decreasing channel", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.imageButton5:
			if(isconnection())
			{
				if(isbluetooth())
			connection.write_bt("MD5X5Y1N");
				else
				{
				WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = manager.getConnectionInfo();
				String address = info.getMacAddress();
				delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD5X5Y1N");
				}

			}
				//connection.write_bt("MD5X5Y1N");
			Toast.makeText(this, "Increasing channel", Toast.LENGTH_SHORT)
					.show();
			break;
			

		}
	}

	private boolean isconnection() {
		SharedPreferences sharedData;
		String image_path = "MySharedString";
		sharedData = getSharedPreferences(image_path, 0);
		int chck = sharedData.getInt("check", 1);
		if (chck == 2)
			return true;
		else
			return false;
	}

	private   boolean isbluetooth() {
		  SharedPreferences sharedData;
		    String image_path = "MySharedString";
		    sharedData = getSharedPreferences(image_path, 0);
		int chck = sharedData.getInt("check2", 1);
		if(chck==2)
			return true;
		else
		return false;
	}
}
