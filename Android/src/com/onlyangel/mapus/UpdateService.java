package com.onlyangel.mapus;

import java.net.URLEncoder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyangel.mapus.tools.NetworkTools;

public class UpdateService extends Service {
	
	public static final String PREFS_NAME = "MyPrefsFile";
	String TAG = UpdateService.class.toString();
	
	LocationManager mlocManager;
	LocationListener mlocListener;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "My Service Created", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onCreate");
		
		NetworkTools nt = new NetworkTools();
        
		nt.fetchSecion("http://thegoapp.appspot.com/ws/createSecionId", handler);

	}
	
	Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
        	String e = (String) message.obj;
        	Log.i(e, MainActivity.class.toString());
        	
        	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("TrackId", e);
            editor.commit();
        	
        	TextView tv = (TextView) MainActivity.entity.findViewById(R.id.base);
        	tv.setText(e);
        	
        	mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    		mlocListener = new MyLocationListener();
    		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1,mlocListener);
        }
    };

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy");
		if (mlocManager!= null)
			mlocManager.removeUpdates(mlocListener);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("TrackId", "");
        editor.commit();
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart");

	}

	public class MyLocationListener implements LocationListener
	{
		private final class HandlerExtension extends Handler {
		}

		public void onLocationChanged(Location loc)
		{
			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		       String silent = settings.getString("TrackId", "");
			String data = "{\"Acuracy\":"+loc.getAccuracy()+",\"Altitude\":"+loc.getAltitude()+",\"Latitude\":"+loc.getLatitude()+",\"Longitude\":"+loc.getLongitude()+",\"Speed\":"+loc.getSpeed()+",\"Time\":"+loc.getTime()+",\"Bearing\":"+loc.getBearing()+",\"Track\":"+silent+"}";
			
			loc.getLatitude();
			loc.getLongitude();
			String Text = "My current location is: " +
					"Latitud = " + loc.getLatitude() +
					"Longitud = " + loc.getLongitude();
			
			Log.i(Text, TAG);

			Toast.makeText( getApplicationContext(),
					Text,
					Toast.LENGTH_SHORT).show();
			NetworkTools nt = new NetworkTools();
			
			nt.fetchSecion("http://thegoapp.appspot.com/ws/pushPos?data="+URLEncoder.encode(data),new HandlerExtension());
			
		}


		public void onProviderDisabled(String provider)
		{
			Toast.makeText( getApplicationContext(),"Gps Disabled",Toast.LENGTH_SHORT ).show();
		}

		public void onProviderEnabled(String provider)
		{
			Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();
		}

		public void onStatusChanged(String provider, int status, Bundle extras)
		{

		}
	}/* End of Class MyLocationListener */
}
