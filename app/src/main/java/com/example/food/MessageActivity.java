package com.example.food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.autonavi.ae.pos.LocationInfo;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements LocationSource,AMapLocationListener,PoiSearch.OnPoiSearchListener {
    private MapView mapView;
    //AMap是地图对象
    private AMap aMap;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private LocationSource.OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private Bundle savedInstanceState;
    private PoiSearch.Query query;
    private  String city;
    private LatLng latLng;
    private PoiSearch poiSearch;
    private List<PoiItem>poiItems;
    private PoiResult poiResult;
    private Poi_Adapter adapter;
    public static  final String KEY_LAT ="lat";
    public static  final String KEY_LNG ="lng";
    public static final String  KEY_DE ="des";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        //初始化控件
        initView();
    }
    /**
     * 获取控件
     */
    private void initView() {
        mapView= (MapView) findViewById(R.id.mapv);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            //设置显示定位按钮 并且可以点击
            UiSettings settings = aMap.getUiSettings();
            aMap.setLocationSource(this);//设置了定位的监听
            // 是否显示定位按钮
            settings.setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        }
        //开始定位
        location();
    }

    private void location() {
        //初始化定位
        try {
            mLocationClient =   new  AMapLocationClient(getApplicationContext());
        }catch (Exception e){
            e.printStackTrace();
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置  true方法开启允许位置模拟
        mLocationOption.setMockEnable(true);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        mLocationClient.startLocation();
    }

    @Override
    public void deactivate() {
        mListener = null;
        if(mLocationClient != null){
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                LocationInfo locationInfo =new LocationInfo();

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);
                    //添加图钉
                    //  aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    city = aMapLocation.getCity();
                    isFirstLoc = false;
                }
                query = new PoiSearch.Query("","050000", aMapLocation.getCity());
                query.setPageSize(20);
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                try {
                    poiSearch =new PoiSearch(this,query);
                } catch (AMapException e) {
                    e.printStackTrace();
                }
                poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude,longitude),5000,true));
                poiSearch.searchPOIAsyn();

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }
    }
   /* protected  void doSearchQuery() throws AMapException {
        aMap.setOnMapClickListener(null);
        int currentPage = 0;
        query = new PoiSearch.Query("","050000", aaa.getCity());
        query.setPageSize(20);
        query.setPageNum(currentPage);
        //LatLonPoint lp =new LatLonPoint(latLng.latitude,latLng.longitude);
        double latitude = aaa.getLatitude();
        double longitude = aaa.getLongitude();
        poiSearch =new PoiSearch(this,query);
        poiSearch.setOnPoiSearchListener((PoiSearch.OnPoiSearchListener) this);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude,longitude),5000,true));
        poiSearch.searchPOIAsyn();
    }*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPoiSearched(PoiResult Result, int i) {
        if(i==0){
            if(Result !=null&&poiResult.getQuery()!=null){
                if(poiResult.getQuery().equals(query)){
                    PoiSearch.Query query = Result.getQuery();
                    poiResult =Result;
                    ArrayList<PoiItem>  poiItems =poiResult.getPois();
                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
                    if(poiItems != null && poiItems.size()>0){
                        adapter = new Poi_Adapter(this,poiItems);
                        ListView mapList =findViewById(R.id.map_list);
                        mapList.setAdapter(adapter);
                        mapList.setOnItemClickListener(new mOnItemClickListener());
                    }
                }
            }
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    private class mOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent =new Intent();
            intent.putExtra(KEY_LAT,poiItems.get(i).getLatLonPoint().getLatitude());
            intent.putExtra(KEY_LNG,poiItems.get(i).getLatLonPoint().getLongitude());
            intent.putExtra(KEY_DE,poiItems.get(i).getTitle());
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
