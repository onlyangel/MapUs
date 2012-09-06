package com.onlyangel.mapus;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class UpdateService extends Service {
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
		
		mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,mlocListener);
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy");
		mlocManager.removeUpdates(mlocListener);
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart");

	}

	public class MyLocationListener implements LocationListener
	{
		public void onLocationChanged(Location loc)
		{
			
			loc.getLatitude();
			loc.getLongitude();
			String Text = "My current location is: " +
					"Latitud = " + loc.getLatitude() +
					"Longitud = " + loc.getLongitude();
			
			Log.i(Text, TAG);

			Toast.makeText( getApplicationContext(),
					Text,
					Toast.LENGTH_SHORT).show();
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
