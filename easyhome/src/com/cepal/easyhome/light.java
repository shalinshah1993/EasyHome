package com.cepal.easyhome;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class light extends Activity implements View.OnClickListener {

	ImageButton ib,ib6;
	boolean toggle = false;

	// ToggleButton tb;
	// connection conn = new connection();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.light);
		setvars();
		setlisteners();
	}

	private void setvars() {

		ib = (ImageButton) findViewById(R.id.imageButton1);
		ib6 = (ImageButton) findViewById(R.id.imageButton6);
		ib6.setBackgroundColor(Color.WHITE);
		// b2 = (Button) findViewById(R.id.Button2);
		// tb = (ToggleButton) findViewById(R.id.toggleButton1);
	}

	private void setlisteners() {

		// tb.setOnClickListener(this);
		ib.setOnClickListener(this);
		//ib6.setOnClickListener(this);
		// b2.setOnClickListener(this);
		// tb.setOnClickListener(this);

	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.imageButton1:

			if (!toggle) {
				if(isconnection())
				{
					if(isbluetooth())
				connection.write_bt("MD2X4Y1N");
					else
					{
					WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					WifiInfo info = manager.getConnectionInfo();
					String address = info.getMacAddress();
					delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD2X4Y1N");
					}
				}
				ib6.setBackgroundColor(Color.YELLOW);
				Toast.makeText(this, "ON", Toast.LENGTH_SHORT)
						.show();
				toggle = true;

			} else {
				//ib.setImageResource(R.drawable.light_off);
				if(isconnection())
				{
					if(isbluetooth())
				connection.write_bt("MD2X4Y2N");
					else
					{
						WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
						WifiInfo info = manager.getConnectionInfo();
						String address = info.getMacAddress();
						delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD2X4Y2N");
					}
				}
				ib6.setBackgroundColor(Color.WHITE);
				Toast.makeText(this, "OFF", Toast.LENGTH_SHORT)
						.show();
				toggle= false;
			}
			break;

		}
	}
	
	private   boolean isconnection() {
		  SharedPreferences sharedData;
		    String image_path = "MySharedString";
		    sharedData = getSharedPreferences(image_path, 0);
		int chck = sharedData.getInt("check", 1);
		if(chck==2)
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
