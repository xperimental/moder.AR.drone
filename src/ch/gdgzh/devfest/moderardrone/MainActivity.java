
package ch.gdgzh.devfest.moderardrone;

import java.net.InetAddress;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.codeminders.ardrone.ARDrone;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity
    extends Activity
{
	public final static String TAG="ARMOD";
	
    private static final long CONNECTION_TIMEOUT = 10000;
  
    final static byte[]                     DEFAULT_DRONE_IP  = { (byte) 192, (byte) 168, (byte) 1, (byte) 1 };

	static ARDrone drone;
	
    @ViewById
    TextView hello;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
        java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
        
        (new DroneStarter()).execute(MainActivity.drone); 
        
        super.onCreate(savedInstanceState);
	}

	@AfterViews
    void afterViews() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    private class DroneStarter extends AsyncTask<ARDrone, Integer, Boolean> {
        
        @Override
        protected Boolean doInBackground(ARDrone... drones) {
            ARDrone drone = drones[0];
            try {
            	Log.i(TAG,"connecting to drone");
            	drone = new ARDrone(InetAddress.getByAddress(DEFAULT_DRONE_IP), 10000, 60000);
                MainActivity.drone = drone; // passing in null objects will not pass object refs
                drone.connect();
                drone.clearEmergencySignal();
                drone.waitForReady(CONNECTION_TIMEOUT);
                drone.playLED(1, 10, 4);
                //drone.addImageListener(MainActivity.mainActivity);
                //drone.selectVideoChannel(ARDrone.VideoChannel.HORIZONTAL_ONLY);
                drone.setCombinedYawMode(false);
                
                return true;
            } catch (Exception e) {
            	Log.i(TAG,"connecting fail " + e);
                try {
                	
                    drone.clearEmergencySignal();
                    drone.clearImageListeners();
                    drone.clearNavDataListeners();
                    drone.clearStatusChangeListeners();
                    drone.disconnect();
                } catch (Exception e1) {
                }
              
            }
            return false;
        }

        protected void onPostExecute(Boolean success) {
        	Log.i(TAG,"connecting post " + success.booleanValue());
            if (success.booleanValue()) {
            	  try
                  {
            		  Thread.sleep(1000);
            		  Log.i(TAG,"clear emergency");
                      drone.clearEmergencySignal();
                      Log.i(TAG,"trim");
                      drone.trim();
                      Log.i(TAG,"takeof");
                      drone.takeOff();
                      
                      Thread.sleep(6000);
            		  
                      Log.i(TAG,"turn");
                      
                      drone .move(0f, 0f,0f,0.9f);
                      
                      Thread.sleep(5000);
                      
                      Log.i(TAG,"turn");
                      
                      
                      for (int i=1;i<36;i++) {
                      drone .move(0f, 0f,0f,0.9f);
               
                      Thread.sleep(1000);}
                      
                      Log.i(TAG,"turn");
                      
                      drone .move(0f, 0f,0f,-0.9f);
               
                      Thread.sleep(5000);
                      
                      Log.i(TAG,"land");
                      drone.land();
                      
                      
                      
                      Thread.sleep(1000);
            		  
                      Log.i(TAG,"disconn");
                      drone.disconnect();
                                        
                  } catch(Throwable e)
                  {
                      e.printStackTrace();
                  }
                /*state.setTextColor(Color.GREEN);
                state.setText("Connected");
                connectButton.setEnabled(false);
                mainActivity.showButtons();
                */
            } else {
                /*state.setTextColor(Color.RED);
                state.setText("Error");
                */
            }
        }
    }
}
