package com.cepal.easyhome;
import java.util.ArrayList;
import java.util.List;
import com.cepal.easyhome.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class VoiceRecog extends Activity implements OnClickListener {
    
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    
    private ListView mList;
    private Handler h;
    private Runnable r;
    private boolean running  = true;
    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setContentView(R.layout.voice_recog);

        // Get display items for later interaction
        ImageButton speakButton = (ImageButton) findViewById(R.id.buttonSpeak);
        
        mList = (ListView) findViewById(android.R.id.list);

        // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            speakButton.setOnClickListener(this);
        } else {
            speakButton.setEnabled(false);
        }
    }

    /**
     * Handle the click on the start recognition button.
     */
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSpeak) {
            startVoiceRecognitionActivity();
            h = new Handler();
            r = new Runnable() {
				
				@Override
				public void run() {	
					if(running){
						startVoiceRecognitionActivity();
						Toast.makeText(VoiceRecog.this, "Speak", 1000).show();
						h.postDelayed(r,15000);
					}
				}
			};
			h.postDelayed(r, 10000);
        }
    }

    /**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    /**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
           // mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
             //       matches));
            if(matches.contains("lights on") || matches.contains("Lights on")){
            	Toast.makeText(VoiceRecog.this, "Lights On", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("lights off") || matches.contains("light off")){
            	Toast.makeText(VoiceRecog.this, "Lights Off", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("fan on") || matches.contains("fan on?") || matches.contains("fan on.")){
            	Toast.makeText(VoiceRecog.this, "Fan On", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("fan off") || matches.contains("fan of")){
            	Toast.makeText(VoiceRecog.this, "Fan Off", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("AC on")){
            	Toast.makeText(VoiceRecog.this, "AC turned On", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("AC off")){
            	Toast.makeText(VoiceRecog.this, "AC turned Off", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("TV on") || matches.contains("television on")){
            	Toast.makeText(VoiceRecog.this, "Television turned On", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("TV off") || matches.contains("television off")){
            	Toast.makeText(VoiceRecog.this, "Television turned Off", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("DVD on")){
            	Toast.makeText(VoiceRecog.this, "DVD turned On", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("DVD off") || matches.contains("DVD of")){
            	Toast.makeText(VoiceRecog.this, "DVD turned Off", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("open window") || matches.contains("open Windows") || matches.contains("open windows")){
            	Toast.makeText(VoiceRecog.this, "Window/Curtain Opened", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("close window") || matches.contains("close Windows") || matches.contains("close windows")){
            	Toast.makeText(VoiceRecog.this, "Window/Curtain Closed", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("switch on")){
            	Toast.makeText(VoiceRecog.this, "Turned the switch on/Plugged in", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("switch OFF") || matches.contains("Switch off") || matches.contains("switch off")){
            	Toast.makeText(VoiceRecog.this, "Turned the switch off/Plugged off", Toast.LENGTH_SHORT).show();
            }else if(matches.contains("Toy") || matches.contains("toy") || matches.contains("toys") || matches.contains("Toys")){
            	Toast.makeText(VoiceRecog.this, "Toy", Toast.LENGTH_SHORT).show();
            }else{
            	Toast.makeText(VoiceRecog.this, "Please speak again command not recognized make sure you saw the tutorial on how to use this app.", Toast.LENGTH_LONG).show();
            }       
        }else if(requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_CANCELED){
        	Toast.makeText(VoiceRecog.this, "Please speak something!!", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	running = false;
    	
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	running = true;
    }
}
