package flaychat.cn.flychat;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class WelcomeActivity extends ActionBarActivity {

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //PushManager.startWork(this, PushConstants.LOGIN_TYPE_API_KEY, "fxc7ctt2N18eY3gKAvYuDgiV");
        mWebView=(WebView) findViewById(R.id.webView);

//		mWebView.setVerticalScrollBarEnabled(false);
//		mWebView.setHorizontalScrollBarEnabled(false);
//
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//		mWebView.loadUrl("http://112.124.22.238:8081/pushserver/start_android.html");
//		mWebView.loadUrl("http://www.baidu.com");
		mWebView.loadUrl(API.WelcomeURL);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
