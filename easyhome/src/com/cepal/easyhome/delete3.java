package com.cepal.easyhome;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class delete3 extends Activity {

	ImageButton ib, ib6;
	boolean toggle = false;
	WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	WifiInfo wInfo = wifiManager.getConnectionInfo();
	String macAddress = wInfo.getMacAddress();

	// ToggleButton tb;
	// connection conn = new connection();
	public static Socket s;

	public static PrintWriter out;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("Done", "came man");
		setconn();
	}

	public static void send_msg(String string_ip) {

		out.println(string_ip);

		// output.println(string_ip);
		// BufferedReader inFromServer = new BufferedReader(new
		// InputStreamReader(s.getInputStream()));
		// String sentence = inFromServer.readLine();
		// Toast.makeText(this, "Sent"+string_ip, Toast.LENGTH_SHORT).show();
		// BufferedReader input = new BufferedReader(new
		// InputStreamReader(s.getInputStream()));

		// read line(s)
		// s.close();
		Log.d("Done", "Data Send");
	}

	public static void s_close()  {
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
static int count = 0;
	public static void setconn()  {

		Log.d("dhaval", "called");
		Log.d("dhaval","Retrying "+count);

		try {
			s = new Socket("1.2.3.4", 2000);
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					s.getOutputStream())), true);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(count<10)
			{
				count++;
				Log.d("dhaval","Retrying "+count);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setconn();
				
			}
		}
		// outgoing stream redirect to socket
		// OutputStream out = s.getOutputStream();
		Log.d("Done", "came man 2 ");
			}

}
