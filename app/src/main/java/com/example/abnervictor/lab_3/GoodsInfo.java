package com.example.abnervictor.lab_3;

import android.content.Intent;
import android.content.IntentFilter;
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
    private int isAddtoCart; // 为1: 无操作, 为2: 加入购物车, 为3: 收藏商品, 为4: 收藏&加入购物车
    private IntentFilter dynamic_filter;
    private DynamicReceiver dynamicReceiver;
    private int Add_Cart;


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
        isAddtoCart = 1;//1表示未加入购物车，2表示加入一件到购物车
        Add_Cart = 0;
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

        // - - - - - - - - 动态注册广播 - - - - - - - -

            dynamic_filter = new IntentFilter();
            dynamic_filter.addAction("Goods_AddtoCart");//添加动态广播的Action
            dynamicReceiver = new DynamicReceiver();
            registerReceiver(dynamicReceiver,dynamic_filter);

        // - - - - - - - - 注册完毕 - - - - - - - -
    }
    private void setListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(isAddtoCart,new Intent(GoodsInfo.this,MainActivity.class).putExtra("goods",goods));
                finish();

                unregisterReceiver(dynamicReceiver);//退出界面后注销广播dynamicReceiver

            }
        });//点击返回图标，结束本Activity, resultCode == 1
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goods.reverseLike();
                boolean like = goods.getLike();
                if(like){
                    if (isAddtoCart == 1) isAddtoCart = 3;
                    else if (isAddtoCart == 2) isAddtoCart = 4;
                    Toast.makeText(getApplicationContext(),"已收藏商品",Toast.LENGTH_SHORT).show();
                    view.setBackgroundResource(R.drawable.full_star);
                }
                else{
                    if (isAddtoCart == 3) isAddtoCart = 1;
                    else if (isAddtoCart == 4) isAddtoCart = 2;
                    Toast.makeText(getApplicationContext(),"已取消收藏商品",Toast.LENGTH_SHORT).show();
                    view.setBackgroundResource(R.drawable.empty_star);
                }
            }
        });
        AddtoCart.setClickable(true);
        AddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAddtoCart == 1) isAddtoCart = 2;
                else if (isAddtoCart == 3) isAddtoCart = 4;
                setResult(2,new Intent(GoodsInfo.this,MainActivity.class).putExtra("goods",goods));
                Toast.makeText(getApplicationContext(),"商品已添加到购物车",Toast.LENGTH_SHORT).show();


                {
                    Intent intentBroadcast = new Intent("Goods_AddtoCart");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("goods", goods);
                    bundle.putInt("Num",Add_Cart);
                    intentBroadcast.putExtras(bundle);
                    sendBroadcast(intentBroadcast);
                }//发送添加到购物车的广播

                Add_Cart += 1; //计算点击的次数
            }
        });//添加到购物车, resultCode == 2
    }
}
