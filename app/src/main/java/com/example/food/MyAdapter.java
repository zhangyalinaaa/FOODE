package com.example.food;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private int[] videos ={R.raw.f2,R.raw.video2,R.raw.video3,R.raw.video5};
    private int[] imgs ={R.mipmap.adert1,R.mipmap.adert2};
    private List<String> mTitles =new ArrayList<>();
    private List<String> mText =new ArrayList<>();
    private Context mContext;
    public MyAdapter(Context context){
        this.mContext =context;
        mTitles.add("我也不知道为什么11111111111111111111");
        mTitles.add("@发生了什么呢你猜好不好吃111111111111111");
        mText.add("22创作的原生-123");
        mText.add("hah创作的原生-123");

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.act_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mThumb.setImageResource(imgs[position % 2]);
        holder.mViView.setVideoURI(Uri.parse("android.resource://" + mContext.getPackageName() + "/" +videos[position%2]));
        //holder.mViView.setVideoURI(Uri.parse("android.resource://com.example.food/"+R.raw.t1));
        holder.mTiTle.setText(mTitles.get(position%2));
        holder.mText.setText(mText.get(position%2));
        holder.mText.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout mRootView;
        ImageView mThumb;
        ImageView mPlay;
        TextView mTiTle;
        TextView mText;
        CusVideoView mViView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mRootView =itemView.findViewById(R.id.mRootView);
            mThumb = itemView.findViewById(R.id.mThumb);
            mPlay =itemView.findViewById(R.id.mPlay);
            mViView =itemView.findViewById(R.id.mViView);
            mTiTle =itemView.findViewById(R.id.mTitle);
            mText = itemView.findViewById(R.id.mText);

        }
    }
}
