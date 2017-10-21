package com.example.abnervictor.lab_3;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by abnervictor on 2017/10/20.
 */

public class GoodsInfo extends AppCompatActivity{
    private ImageView goodsPic;//商品图
    private ImageView back; //返回
    private ImageView star; //收藏
    private TextView name; //商品名
    private TextView price; //价格
    private TextView info_1; //信息1
    private TextView info_2; //信息2
    private ListView operationList; //操作列表
    private Goods goods; //商品信息class
    private ImageView AddtoCart; //加入购物车
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        findView();
        initData();
        setListener();
    }
    private void findView(){
        setContentView(R.layout.goods_info);
        goodsPic = (ImageView) findViewById(R.id.goods_pic);
        back = (ImageView) findViewById(R.id.back_button);
        star = (ImageView) findViewById(R.id.star_button);
        name = (TextView) findViewById(R.id.goods_name);
        price = (TextView) findViewById(R.id.price);
        info_1 = (TextView) findViewById(R.id.info1);
        info_2 = (TextView) findViewById(R.id.info2);
        operationList = (ListView) findViewById(R.id.operationList);
        AddtoCart = (ImageView) findViewById(R.id.add_to_cart);
    }
    private void initData(){
        Resources resources = getResources();
        goods = (Goods) getIntent().getExtras().get("goods");
        if (goods != null){
            goodsPic.setImageResource(goods.getPicID());
            name.setText(goods.GetString(1));
            price.setText(goods.GetString(2));
            info_1.setText(goods.GetString(3));
            info_2.setText(goods.GetString(4));
        }
        String[] operations = new String[]{resources.getString(R.string.click_to_buy),resources.getString(R.string.share_contacts),resources.getString(R.string.no_interest),resources.getString(R.string.more_info)};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.info_list,R.id.string,operations);
        operationList.setAdapter(arrayAdapter);
        if(goods.getLike()){
            star.setBackgroundResource(R.drawable.full_star);
        }
        else{
            star.setBackgroundResource(R.drawable.empty_star);
        }
    }
    private void setListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(2,new Intent(GoodsInfo.this,MainActivity.class).putExtra("goods",goods));
                finish();
            }
        });//点击返回图标，结束本Activity, resultCode == 2
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goods.reverseLike();
                boolean like = goods.getLike();
                if(like){
                    Toast.makeText(getApplicationContext(),"已收藏商品",Toast.LENGTH_SHORT).show();
                    view.setBackgroundResource(R.drawable.full_star);
                }
                else{
                    Toast.makeText(getApplicationContext(),"已取消收藏商品",Toast.LENGTH_SHORT).show();
                    view.setBackgroundResource(R.drawable.empty_star);
                }
            }
        });
        AddtoCart.setClickable(true);
        AddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(1,new Intent(GoodsInfo.this,MainActivity.class).putExtra("goods",goods));
                Toast.makeText(getApplicationContext(),"商品已添加到购物车",Toast.LENGTH_SHORT).show();
            }
        });//添加到购物车, resultCode == 1
    }
}
