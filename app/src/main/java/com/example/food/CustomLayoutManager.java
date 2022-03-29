package com.example.food;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLayoutManager extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener {
   private int mDrift;
   private PagerSnapHelper mPagerSnapHelper;
   private OnPageSlideListener mOnPageSlideListener;
    public CustomLayoutManager(Context context){
        super(context);
    }
    public CustomLayoutManager(Context context,int orientation,boolean reverseLayout){
        super(context, orientation, reverseLayout);
        mPagerSnapHelper = new PagerSnapHelper();
    }
    @Override
    public void onAttachedToWindow(RecyclerView view){
        view.addOnChildAttachStateChangeListener(this);
        mPagerSnapHelper.attachToRecyclerView(view);
        super.onAttachedToWindow(view);
    }
    @Override // add Item
    public void onChildViewAttachedToWindow(@NonNull View view) {
      //播放video,并判断将要播放的属于上一个或者下个
        if(mDrift > 0){
            if(mOnPageSlideListener != null)
                mOnPageSlideListener.onPageSelected(getPosition(view),true);
        } else {
            if(mOnPageSlideListener != null)
                mOnPageSlideListener.onPageSelected(getPosition(view),false);
        }
    }

    @Override //move Item
    public void onChildViewDetachedFromWindow(@NonNull View view) {
        if(mDrift >= 0){
            if (mOnPageSlideListener != null)
                mOnPageSlideListener.onPageRelease(true,getPosition(view));
        } else {
            if (mOnPageSlideListener != null)
                mOnPageSlideListener.onPageRelease(false,getPosition(view));
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        switch (state){
            case RecyclerView.SCROLL_STATE_IDLE:
                View view = mPagerSnapHelper.findSnapView(this);
                int position =getPosition(view);
                if (mOnPageSlideListener != null){
                    mOnPageSlideListener.onPageSelected(position,position==getItemCount() -1);
                }
                break;
        }

    }
    @Override
    public int scrollVerticallyBy(int food,RecyclerView.Recycler recycler,RecyclerView.State state){
        this.mDrift = food;
        return super.scrollVerticallyBy(food, recycler, state);
    }

    public void setmOnPageSlideListener(OnPageSlideListener mOnViewPageSlideListener) {
        this.mOnPageSlideListener = mOnViewPageSlideListener;
    }
}
