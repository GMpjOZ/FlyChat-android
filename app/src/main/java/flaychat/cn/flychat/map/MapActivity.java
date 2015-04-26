package flaychat.cn.flychat.map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flaychat.cn.flychat.API;
import flaychat.cn.flychat.BaseActivity;
import flaychat.cn.flychat.R;
import flaychat.cn.flychat.model.User;
import flaychat.cn.flychat.http.BaseResponse;
import flaychat.cn.flychat.http.RequestListener;

public class MapActivity extends BaseActivity {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private LatLng ll;
    private LocationClient mLocationClient = null;
    private boolean isFirstLoc = true;// 是否首次定位
    private List<Marker> markers=new ArrayList<Marker>();
    private List<User> users=new ArrayList<User>();
    private InfoWindow mInfoWindow;


    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location == null || mMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                 ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                Log.w("纬度",location.getLatitude()+"");
                Log.w("经度",location.getLongitude()+"");
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }

            PostLocation postLocation=new PostLocation();
            new Thread(postLocation).start();
            
            ShowUsersThread thread=new ShowUsersThread();
            new Thread(thread).start();


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数

        setContentView(R.layout.activity_map);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMaxAndMinZoomLevel(20,15);
        UiSettings ui=mBaiduMap.getUiSettings();
        ui.setCompassEnabled(false);
        ui.setRotateGesturesEnabled(false);

        Log.w("最小缩放",mBaiduMap.getMinZoomLevel()+"");
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(100000);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.w("图标",marker.getPosition()+"");
                Button button = new Button(getApplicationContext());
                button.setBackgroundResource(R.drawable.popup);
                for(int i=0;i<markers.size();i++){
                    Log.w("图标",marker+"");
                    Log.w("图标循环",markers.get(i)+"");

                    if(marker==markers.get(i)){
                        button.setText(users.get(i).getName());
                        button.setTextColor(000000);
                        LatLng m_point = marker.getPosition();
                        mInfoWindow = new InfoWindow(button,m_point,-47);
                        mBaiduMap.showInfoWindow(mInfoWindow);
                    }

                }
                return false;
            }
        });


    }

    class PostLocation implements Runnable{
        @Override
        public void run() {
            Map<String,String> map=new HashMap<String,String>();
            map.put("latitude",ll.latitude+"");
            map.put("longitude",ll.longitude+"");
            map.put("id",123+"");
            Log.w("","发送自己位置信息");
            mHttpClient.post(API.PostLocation,map,0,new RequestListener() {
                @Override
                public void onPreRequest() {

                }

                @Override
                public void onRequestSuccess(BaseResponse response) {

                    Log.w("","发送信息成功");
                }

                @Override
                public void onRequestError(int code, String msg) {
                    Log.e("","发送请求错误");
                }

                @Override
                public void onRequestFail(int code, String msg) {
                    Log.e("","发送请求错误");
                }
            });
        }
    }

     class ShowUsersThread implements Runnable {

        public void run() {

            Log.w("","获取用户信息");
            mHttpClient.get(API.GetUser,0, new RequestListener(){

                @Override
                public void onPreRequest() {

                }

                @Override
                public void onRequestSuccess(BaseResponse response) {

                    Log.w("","获取用户信息成功");
                    List<User> allusers=response.getList(User.class);

                    List<User> users1=searchUser(allusers);
                    Message msg = Message.obtain();
                    msg.obj=users1;
                    updateMarkerHandler.sendMessage(msg);


                }

                @Override
                public void onRequestError(int code, String msg) {

                    Log.e("","获取请求错误");
                }

                @Override
                public void onRequestFail(int code, String msg) {

                    Log.e("","获取请求失败");
                }
            });

//            List<User> allusers=getUser();
////
//                    users=searchUser(allusers);
//                    Message msg = Message.obtain();
//                    msg.obj=users;
//                    updateMarkerHandler.sendMessage(msg);

        }
    }
//
//    public List<User> getUser(){
//        User user1=new User();
//        user1.setLatitude(31.9131313);
//        user1.setLongitude(118.79111);
//        user1.setName("aa1");
//        User user2=new User();
//        user2.setLatitude(31.91233);
//        user2.setLongitude(118.795345);
//        user2.setName("aa2");
//        User user3=new User();
//        user3.setLatitude(31.914323);
//        user3.setLongitude(118.795345);
//        user3.setName("aa3");
//        User user4=new User();
//        user4.setLatitude(31.9124234);
//        user4.setLongitude(118.7923422);
//        user4.setName("aa4");
//        List<User> users=new ArrayList<User>();
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//        users.add(user4);
//        return users;
//    }
    public List<User> searchUser(List<User> allusers){

        List<User> newUser=new ArrayList<User>();
        for(User user:allusers){
            LatLng point=new LatLng(user.getLatitude(),user.getLongitude());
            double distance=DistanceUtil.getDistance(ll,point);
            Log.w("距离",distance+"");
            if(distance<1000){
                newUser.add(user);
            }
        }
        return newUser;
    }

    //创建handler
    Handler updateMarkerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            List<User> users= (List<User>) msg.obj;


            int i=0;
            for (User user:users) {

                LatLng point=new LatLng(user.getLatitude(),user.getLongitude());
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_marki);
//构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
//             mBaiduMap = nMapView.getMap();
//在地图上添加Marker，并显示

                markers.add((Marker) mBaiduMap.addOverlay(option));

            }
       }


    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
