package com.example.abnervictor.lab_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abnervictor on 2017/10/19.
 */

public class GoodsListAdapter extends BaseAdapter {
    private Context context;
    private List<Goods> dataList;

    public GoodsListAdapter(Context context, List<Goods> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    public List<Goods> getDataList(){
        return dataList;
    }

    @Override
    public int getCount(){
        if(isNULL()){
            return 0;
        }
        return dataList.size();
    }

    @Override
    public Object getItem(int i){
        if (isNULL()){
            return null;
        }
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        View convertView;
        ViewHolder viewHolder;
        if(view == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.goods_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.image = (TextView) convertView.findViewById(R.id.first);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        }
        else{
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setText(dataList.get(i).getFirst());
        viewHolder.name.setText(dataList.get(i).GetString(1));
        return convertView;
    }

    private boolean isNULL(){
        return dataList == null;
    }

    private class ViewHolder{
        public TextView image;
        public TextView name;
    }
}
