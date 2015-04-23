package flaychat.cn.flychat;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import flaychat.cn.flychat.http.VolleyHttpClient;


public class BaseActivity extends ActionBarActivity {

	protected VolleyHttpClient mHttpClient ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHttpClient=mHttpClient.getInstance(this);
		}
	

	
}
