package com.example.food;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.food.fragment.Fragmentp1;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends FragmentActivity implements View.OnClickListener {

    LinearLayout lintonOne;
    LinearLayout lintonTwo;
    LinearLayout lintonThree;
    LinearLayout lintonFour;
    ViewPager mViewPager;
    List<Fragment> fragmentList;
    int screenWidth;
    //当前选中的项
    int currenttab=-1;
    Fragmentp1 oneFragment;
    Fragmentplimit twoFragment;
    Fragmentplike threeFragment;
    Fragmentpsave fourFragment;
    private TextView tvmain;
    private TextView tvcontact;
    private TextView tvmy;
    private TextView tvset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);
        lintonOne= (LinearLayout) findViewById(R.id.lin_one);
        lintonTwo=(LinearLayout) findViewById(R.id.lin_two);
        lintonThree=(LinearLayout) findViewById(R.id.lin_three);
        lintonFour=(LinearLayout) findViewById(R.id.lin_four);


        tvmain = (TextView) findViewById(R.id.work);
        tvcontact = (TextView) findViewById(R.id.limit);
        tvmy = (TextView) findViewById(R.id.like);
        tvset = (TextView) findViewById(R.id.btn1);


        lintonOne.setOnClickListener(this);
        lintonTwo.setOnClickListener(this);
        lintonThree.setOnClickListener(this);
        lintonFour.setOnClickListener(this);

        mViewPager=(ViewPager) findViewById(R.id.vp);

        fragmentList=new ArrayList<Fragment>();
        oneFragment=  new Fragmentp1();
        twoFragment=  new Fragmentplimit();
        threeFragment = new Fragmentplike();
        fourFragment= new Fragmentpsave();

        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(threeFragment);
        fragmentList.add(fourFragment);

        mViewPager.setAdapter(new PersonalActivity.MyFrageStatePagerAdapter(getSupportFragmentManager()));

        tvmain.setTextColor(Color.RED);
    }


    /**
     * 定义自己的ViewPager适配器。
     * 也可以使用FragmentPagerAdapter。关于这两者之间的区别，可以自己去搜一下。
     */
    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter
    {

        public MyFrageStatePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动
         */
        @Override
        public void finishUpdate(ViewGroup container)
        {
            super.finishUpdate(container);//这句话要放在最前面，否则会报错
            //获取当前的视图是位于ViewGroup的第几个位置，用来更新对应的覆盖层所在的位置
            int currentItem=mViewPager.getCurrentItem();
            if (currentItem==currenttab)
            {
                return ;
            }
            //    imageMove(mViewPager.getCurrentItem());
            currenttab=mViewPager.getCurrentItem();
            if (currenttab==0){
                tvmain.setTextColor(Color.RED);
                lintonOne.setBackgroundColor(Color.GREEN);
            }else{
                tvmain.setTextColor(Color.BLACK);
                lintonOne.setBackgroundColor(Color.WHITE);
            }
            if (currenttab==1){
                tvcontact.setTextColor(Color.RED);
                lintonTwo.setBackgroundColor(Color.GREEN);
            }else{
                tvcontact.setTextColor(Color.BLACK);
                lintonTwo.setBackgroundColor(Color.WHITE);
            }
            if (currenttab==2){
                tvmy.setTextColor(Color.RED);
                lintonThree.setBackgroundColor(Color.GREEN);

            }else{
                tvmy.setTextColor(Color.BLACK);
                lintonThree.setBackgroundColor(Color.WHITE);
            }
            if (currenttab==3){
                tvset.setTextColor(Color.RED);
                lintonFour.setBackgroundColor(Color.GREEN);
            }else{
                tvset.setTextColor(Color.BLACK);
                lintonFour.setBackgroundColor(Color.WHITE);
            }
        }

    }



    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.lin_one:
                changeView(0);
                break;
            case R.id.lin_two:
                changeView(1);
                break;
            case R.id.lin_three:
                changeView(2);
                break;
            case R.id.lin_four:
                changeView(3);
                break;
            default:
                break;
        }
    }
    //手动设置ViewPager要显示的视图
    private void changeView(int desTab)
    {

        mViewPager.setCurrentItem(desTab, true);
    }

    public void startActivitymain(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    public void startActivitym(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    public void startActivityf(View view) {
        startActivity(new Intent(this,MainActivity2.class));
    }

     public void startActivitydata(View view) {
         startActivity(new Intent(this,personadata.class));
     }

}
