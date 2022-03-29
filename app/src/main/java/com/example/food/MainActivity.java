package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;

public class MainActivity extends AppCompatActivity {
   private static final String TAG = MainActivity.class.getSimpleName();
   private RecyclerView mRview;
   private CustomLayoutManager mLayoutManager;
   private TextView textView;
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private LocationSource.OnLocationChangedListener mListener = null;
    private AMap aMap;
    AMapLocation aMapLocation;
    private String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView video = findViewById(R.id.video);
        initView();
        initListener();
        //location();
        /*text = aMapLocation.getDistrict();
        textView =findViewById(R.id.loc);
        textView.setText(text);*/
        video.setOnClickListener(v->{
            startActivity(new Intent(this,UploadVideo.class));
                });
    }

    private void initListener() {
        mLayoutManager.setmOnPageSlideListener(new OnPageSlideListener() {
            @Override
            public void onPageRelease(boolean isNext, int position) {
                int index;
                if(isNext){
                    index=0;
                }else{
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position, boolean isNext) {
                playVideo();
            }
        });
    }

    private void initView() {
        mRview =findViewById(R.id.mRview);
        mLayoutManager =new CustomLayoutManager(this, OrientationHelper.VERTICAL,false);
        MyAdapter mAdapter = new MyAdapter(this);
        mRview.setLayoutManager(mLayoutManager);
        mRview.setAdapter(mAdapter);
    }
    private void playVideo() {
        View itemView = mRview.getChildAt(0);
        final CusVideoView mViView =itemView.findViewById(R.id.mViView);
        final ImageView mPlay = itemView.findViewById(R.id.mPlay);
        final ImageView mThumb = itemView.findViewById(R.id.mThumb);
        final MediaPlayer[] mMediaPlayer = new MediaPlayer[1];
        mViView.start();
        mViView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mMediaPlayer[0] = mp;
                mp.setLooping(true);
                mThumb.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });
            mPlay.setOnClickListener(new View.OnClickListener() {
                boolean isPlaying = true;
                @Override
                public void onClick(View view) {
                    if (mViView.isPlaying()){
                        mPlay.animate().alpha(1f).start();
                        mViView.pause();
                        isPlaying = false;
                    }else {
                        mPlay.animate().alpha(0f).start();
                        mViView.start();
                        isPlaying =true;
                    }
                }
            });

    }
    private  void releaseVideo(int index){
        View itemView =mRview.getChildAt(index);
        final CusVideoView mVideoView =itemView.findViewById(R.id.mViView);
        final ImageView mThumb = itemView.findViewById(R.id.mThumb) ;
        final ImageView mPlay = itemView.findViewById(R.id.mPlay);
        mVideoView.stopNestedScroll();
        mThumb.animate().alpha(1).start();
        mPlay.animate().alpha(0f).start();
    }
    public void startActivity(View view) {
        startActivity(new Intent(this,PersonalActivity.class));
    }

    public void startActivitym(View view) {
        startActivity(new Intent(this,MessageActivity.class));
    }

    public void startActivityf(View view) {
        startActivity(new Intent(this,ShopActivity.class));
    }
}