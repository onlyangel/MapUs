package com.onlyangel.mapus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.onlyangel.mapus.tools.NetworkTools;

public class MainActivity extends Activity {
	
	static MainActivity entity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entity=this;
        
        this.startService(new Intent(this, UpdateService.class));
        
        NetworkTools nt = new NetworkTools();
        
		nt.fetchSecion("http://thegoapp.appspot.com/ws/createSecionId", handler);
		
    }
    
    @Override	
	public void onDestroy() {	
    	this.stopService(new Intent(this, UpdateService.class));
    	super.onDestroy();
	}
    
    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
        	String e = (String) message.obj;
        	Log.i(e, MainActivity.class.toString());
        	
        	TextView tv = (TextView) MainActivity.entity.findViewById(R.id.base);
        	tv.setText(e);
        }
    };
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
}
