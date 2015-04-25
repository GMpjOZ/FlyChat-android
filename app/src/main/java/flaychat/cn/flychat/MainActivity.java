package flaychat.cn.flychat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

<<<<<<< HEAD
import flaychat.cn.flychat.chat.ChatActivity;
=======
import flaychat.cn.flychat.map.MapActivity;
>>>>>>> e29a9a06b8e71373ca5d0528a3b7a9a3260d4095


public class MainActivity extends BaseActivity {

    private Button welcome;
    private Button map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcome= (Button) findViewById(R.id.welcome);

        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,WelcomeActivity.class);
                startActivity(intent);
            }
        });
        map= (Button) findViewById(R.id.map);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public void click(View v){
        Intent toChatIntent = new Intent(MainActivity.this, ChatActivity.class);
        startActivity(toChatIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
