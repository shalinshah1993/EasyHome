package com.cepal.easyhome;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;



public class BluetoothSendService {
	
	private static final String TAG = "BluetoothSendService";
    private static final boolean D = true;
	
	// Name for the SDP record when creating server socket
    private static final String NAME = "EasyHome";

    // Unique UUID for this application
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    
    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    //private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private AcceptThread mAcceptThread;
    private int mState;
    
 // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    public static final int STATE_CAN_NOT_CONNECT = 4;

    
    public BluetoothSendService(Context context, Handler handler) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
    }
    
    private synchronized void setState(int state) {
        //if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;

        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(connection.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }
    
    
    public synchronized void connect(BluetoothDevice device) {
        if (D) Log.d(TAG, "connect to: " + device);

        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        
        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
        Log.d("dhaval","Bluetooth service state connecting");
        
    }


    public synchronized void manageConnectedSocket(BluetoothSocket socket, BluetoothDevice device) {
        //if (D) Log.d(TAG, "connected");

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        // Cancel the accept thread because we only want to connect to one device
        //if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}
        if (D) Log.d(TAG, "I am in manageConnected Socket" + this);
        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(connection.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(connection.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        setState(STATE_CONNECTED);
    }

    public synchronized int getState() {
        return mState;
    }
    
    public boolean write(byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
        	Log.d(TAG,"Enters write1");
            if (mState != STATE_CONNECTED)
            {             	
            	return false;
            } 
            Log.d(TAG,String.valueOf(getState())); 
           
           r = mConnectedThread;
           r.write(out);
           return true;
          
        }
        // Perform the write unsynchronized
        
    }
    
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;
     
        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) { 
            	Log.d(TAG, "listen() failed", e);
            }
            mmServerSocket = tmp;
        }
     
        public void run() {
            BluetoothSocket socket = null;
            if (D) Log.d(TAG, "BEGIN mAcceptThread" + this);
            setName("AcceptThread");
            // Keep listening until exception occurs or a socket is returned
            while (mState != STATE_CONNECTED) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                	 if (D) Log.d(TAG, "Not Working Mate" + this);
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                	synchronized (BluetoothSendService.this) {
                        switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                        	if (D) Log.d(TAG, "I am connecting mate" + this);
                            // Situation normal. Start the connected thread.
                            manageConnectedSocket(socket, socket.getRemoteDevice());
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:
                            // Either not ready or already connected. Terminate new socket.
                          /*  try {
                                //socket.close();
                                
                            } catch (IOException e) {
                                Log.e(TAG, "Could not close unwanted socket", e);
                            }*/
                            break;
                }
                	}
                }
            }
            
        }
     
        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }
    
    
    public synchronized void start() {
        if (D) Log.d(TAG, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        // Start the thread to listen on a BluetoothServerSocket
        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
        setState(STATE_LISTEN);
    }
    

    
    
 public class ConnectThread extends Thread {
    
	public final BluetoothSocket mmSocket;
     public final BluetoothDevice mmDevice;
 
    public ConnectThread(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
    	if(D) Log.d(TAG, "connect thread");
        BluetoothSocket tmp = null;
        mmDevice = device;
       
        //new AlertDialog.Builder(getApplicationContext()).setTitle("In connect").setMessage("Watch out!").setNeutralButton("Close", null).show();
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }
        mmSocket = tmp;
    }
 
    public void run() {
        // Cancel discovery because it will slow down the connection
        mAdapter.cancelDiscovery();
        //setName("ConnectThread");
        if(D) Log.d(TAG, "inside connect run");
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
        } catch (IOException connectException) {
        	if(D) Log.d(TAG, "cannot connect socket");
            // Unable to connect; close the socket and get out
            //try {
            //    mmSocket.close();
           // } catch (IOException closeException) { 
            	// Log.e(TAG, "unable to close() socket during connection failure");
            //}
        	setState(STATE_CAN_NOT_CONNECT);
        	Log.d("dhaval","can not connect :( ");
        	
            return;
        }
 
        // Do work to manage the connection (in a separate thread)
        manageConnectedSocket(mmSocket,mmDevice);
    }
 
    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        //try {
         //   mmSocket.close();
      //s  } catch (IOException e) { }
    }
}

private class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
 
    public ConnectedThread(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        if(D) Log.d(TAG, "In connect thread");
        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }
 
        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }
 
    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()
 
        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                // Send the obtained bytes to the UI activity
                mHandler.obtainMessage(connection.MESSAGE_READ, bytes, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                break;
            }
        }
    }
 
    /* Call this from the main activity to send data to the remote device */
    public boolean write(byte[] bytes) {
        try {
        	Log.d(TAG,"step1");
            mmOutStream.write(bytes);
          Log.d(TAG,"step2");
            mHandler.obtainMessage(connection.MESSAGE_WRITE, -1, -1, bytes)
            .sendToTarget();
            Log.d(TAG,"step3");
        } catch (Exception e) { Log.d(TAG,"Write error"); }
        return true;
    }
 
    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}




}
