package com.onlyangel.mapus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	
	static MainActivity entity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entity=this;
        
        this.startService(new Intent(this, UpdateService.class));

    }
    
    @Override	
	public void onDestroy() {	
    	this.stopService(new Intent(this, UpdateService.class));
    	super.onDestroy();
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
}
