package com.example.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amap.api.services.core.PoiItem;

import java.util.List;

public class Poi_Adapter extends BaseAdapter {
    private List<PoiItem> poiItems;
    private Context context;
    private LayoutInflater mInflater;
    public Poi_Adapter(Context context,List<PoiItem>list){
        poiItems =list;
        this.mInflater =LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return poiItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder viewHolder;
        if(view == null){
            view =mInflater.inflate(R.layout.item_poi_search,viewGroup,false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        PoiItem poiItem =poiItems.get(i);
        viewHolder.poitext.setText(poiItem.getTitle());
        viewHolder.poide.setText(poiItem.getSnippet());
        return view;
    }


    public class MyViewHolder {
        private TextView poitext;
        private TextView poide;
        public MyViewHolder(@NonNull View view){
            poitext=view.findViewById(R.id.poi_text);
            poide =view.findViewById(R.id.poi_de);
        }
    }
}
