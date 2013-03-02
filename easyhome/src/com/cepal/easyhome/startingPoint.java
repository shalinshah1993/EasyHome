package com.cepal.easyhome;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class startingPoint extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		Thread timer1 = new Thread() {
			public void run() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent startesp = new Intent("com.cepal.easyhome.CHOOSE");
					startActivity(startesp);
				}
			}

		};
		timer1.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
