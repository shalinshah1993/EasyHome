package com.cepal.easyhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.cepal.easyhome.connection;

public class choose extends Activity {

	// ImageButton ib;
	boolean toggle = true;;
	Button b1, b2;
	SharedPreferences someData;
	public static String image_path = "MySharedString";
	SharedPreferences.Editor editor;

	// ToggleButton tb;
	// connection conn = new connection();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// setContentView(R.layout.choose);
		ask();
		setvars();
		// setlisteners();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		ask();
	}

	private void ask() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent int1 = null;
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					// Yes button clicked
					editor.putInt("check", 1);
					editor.commit();
					int1 = new Intent("com.cepal.easyhome.HOME_MENU");
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked
					editor.putInt("check", 2);
					editor.commit();
					int1 = new Intent("com.cepal.easyhome.CHOOSE2");

					break;
				}
				startActivity(int1);
				finish();
				
			}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Do want a demo or You want to connect to your Device ? ")
				.setPositiveButton("Demo", dialogClickListener)
				.setNegativeButton("Connect", dialogClickListener).show();

	}

	private void setvars() {

		// ib = (ImageButton) findViewById(R.id.imageButton1);
		// b1 = (Button) findViewById(R.id.button1);
		// b2 = (Button) findViewById(R.id.button2);
		// tb = (ToggleButton) findViewById(R.id.toggleButton1);
		someData = getSharedPreferences(image_path, 0);
		editor = someData.edit();

	}

	private void setlisteners() {

		// tb.setOnClickListener(this);
		// ib.setOnClickListener(this);
		// b1.setOnClickListener(this);
		// b2.setOnClickListener(this);
		// tb.setOnClickListener(this);

	}
	/*
	 * public void onClick(View v) { Intent int1 = null; switch (v.getId()) {
	 * case R.id.button1: editor.putInt("check", 1); editor.commit(); int1 = new
	 * Intent("com.cepal.easyhome.CONNECTION"); break; case R.id.button2:
	 * editor.putInt("check", 1); editor.commit(); int1 = new
	 * Intent("com.cepal.easyhome.HOME_MENU"); break; } startActivity(int1); }
	 */

}