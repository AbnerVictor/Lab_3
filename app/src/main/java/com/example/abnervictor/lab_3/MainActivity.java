package com.example.abnervictor.lab_3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class MainActivity extends AppCompatActivity {

    private ListView shoppingList;
    private RecyclerView mRecyclerView;
    private AlertDialog.Builder builder;
    private SimpleAdapter simpleAdapter;
    private CommonAdapter<Map<String,Object>> commonAdapter;
    private FloatingActionButton fab;
    private List<Goods> Data = new ArrayList<>();
    private List<Goods> shoppinglist = new ArrayList<>();
    private List<Map<String,Object>> listItems1 = new ArrayList<>();
    private List<Map<String,Object>> listItems2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        InitData();
        setListner();
        Brocast();

        EventBus.getDefault().register(this);//
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // This method will be called when a MessageEvent is posted
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){

        if(event.getGoods() != null){
            Goods c = event.getGoods();
            Map<String,Object> listitem = new LinkedHashMap<>();
            assert c != null;
            listitem.put("firstLetter",c.getFirst());
            listitem.put("name",c.GetString(1));
            listitem.put("price",c.GetString(2));
            shoppinglist.add(c);
            listItems2.add(listitem);

            Map<String, Object> listitem2 = new LinkedHashMap<>();
            Goods top = shoppinglist.get(0);
            listitem2.put("firstLetter", "*");
            listitem2.put("name", top.GetString(1));
            listitem2.put("price", top.addPrice(c));
            listItems2.remove(0);
            listItems2.add(0,listitem2);
            shoppinglist.remove(0);
            shoppinglist.add(0,top);
            simpleAdapter.notifyDataSetChanged();
        }//向购物车内添加商品

        if (event.JumptoCart()){
            if(mRecyclerView.getVisibility()==View.VISIBLE){
                mRecyclerView.setVisibility(View.GONE);
                shoppingList.setVisibility(View.VISIBLE);
                fab.setImageResource(R.drawable.mainpage);
            }
        }//跳转到购物车

    }//接受通过EventBus传来的信息

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        //Toast.makeText(getApplicationContext(),"requestCode = "+Integer.toString(requestcode)+" and resultCode = "+Integer.toString(resultcode),Toast.LENGTH_SHORT).show();
    }

    private void findView(){
        setContentView(R.layout.activity_main);
        shoppingList = (ListView) findViewById(R.id.shopping_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.goods_list);
        builder = new AlertDialog.Builder(this);
        fab = (FloatingActionButton) findViewById(R.id.floating_button);
    }

    private void InitData() {
        Data.add(new Goods("Enchated Forest", 5.00, "作者", "Johanna Basford", R.drawable.enchatedforest));
        Data.add(new Goods("Arla Milk", 59.00, "产地", "德国", R.drawable.arla));
        Data.add(new Goods("Devondale Milk", 79.00, "产地", "澳大利亚", R.drawable.devondale));
        Data.add(new Goods("Kindle Oasis", 2399.00, "容量", "8GB", R.drawable.kindle));
        Data.add(new Goods("waitrose 早餐麦片", 179.00, "重量", "2kg", R.drawable.waitrose));
        Data.add(new Goods("Mcvitie's 饼干", 14.90, "产地", "英国", R.drawable.mcvitie));
        Data.add(new Goods("Ferrero Rocher", 132.59, "重量", "300g", R.drawable.ferrero));
        Data.add(new Goods("Maltesers", 141.43, "重量", "118g", R.drawable.maltesers));
        Data.add(new Goods("Lindt", 139.42, "重量", "249g", R.drawable.lindt));
        Data.add(new Goods("Borggreve", 28.90, "重量", "640g", R.drawable.borggreve));

        {
            Map<String, Object> listitem = new LinkedHashMap<>();
            Goods c = new Goods("购物车", 0, null, null, 0);
            listitem.put("firstLetter", "*");
            listitem.put("name", c.GetString(1));
            listitem.put("price", c.GetString(2));
            listItems2.add(listitem);
            shoppinglist.add(c);
        }//为购物车添加首行

        for (Goods c : Data) {
            Map<String, Object> listitem = new LinkedHashMap<>();
            listitem.put("name", c.GetString(1));
            listitem.put("firstLetter", c.getFirst());
            listItems1.add(listitem);
        }//填充列表
        // - - - ListView - - - //
        simpleAdapter = new SimpleAdapter(this, listItems2, R.layout.goods_list_item, new String[]{"firstLetter", "name", "price"}, new int[]{R.id.first, R.id.name, R.id.price_});
        shoppingList.setAdapter(simpleAdapter);
        // - - - ListView - - - //

        // - - - RecyclerView - - - //
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//线性显示
        commonAdapter = new CommonAdapter<Map<String, Object>>(this, R.layout.goods_list_item, listItems1) {
            //通过viewholder修改视图的内容
            @Override
            public void convert(ViewHolder holder, Map<String, Object> stringObjectMap) {
                TextView first = holder.getView(R.id.first);
                first.setText(stringObjectMap.get("firstLetter").toString());
                TextView name = holder.getView(R.id.name);
                name.setText(stringObjectMap.get("name").toString());
                TextView price_ = holder.getView(R.id.price_);
                price_.setText("");
            }
        };
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, GoodsInfo.class);

                MessageEvent message = new MessageEvent(false,null);
                message.RefreshGoodsInfo(); //清理未终结的GoodsInfo
                EventBus.getDefault().post(message);

                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", Data.get(position));
                intent.putExtras(bundle);
                startActivity(intent);//requestCode == position
            }//跳转到商品详情界面

            @Override
            public void onLongClick(int position) {
                Data.remove(position);
                commonAdapter.removeItem(position);
                Toast.makeText(getApplicationContext(), "移除第" + (position+1) + "个商品", Toast.LENGTH_SHORT).show();
            }
        });
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(commonAdapter);
        animationAdapter.setDuration(1000); //设置动画
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
        builder.setTitle("移除商品");
        // - - - RecyclerView - - - //

    }
    private void setListner(){
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mRecyclerView.getVisibility()==View.VISIBLE){
                    mRecyclerView.setVisibility(View.GONE);
                    shoppingList.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.drawable.mainpage);
                }
                else{
                    mRecyclerView.setVisibility(View.VISIBLE);
                    shoppingList.setVisibility(View.GONE);
                    fab.setImageResource(R.drawable.shoplist);
                }
            }
        });//浮动按钮切换商品列表与购物车
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });

        shoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0) return;//购物车标题栏不做响应
                Intent intent = new Intent(MainActivity.this, GoodsInfo.class);

                MessageEvent message = new MessageEvent(false,null);
                message.RefreshGoodsInfo(); //清理未终结的GoodsInfo
                EventBus.getDefault().post(message);

                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", shoppinglist.get(i));
                intent.putExtras(bundle);
                startActivity(intent);//
            }
        });//点击进入商品详情
        shoppingList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                if (pos==0) return true;
                builder.setMessage("从购物车移除"+shoppinglist.get(pos).GetString(1)+"?");//GetString(1)商品名
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        {
                            Map<String, Object> listitem = new LinkedHashMap<>();
                            Goods c = shoppinglist.get(0);
                            listitem.put("firstLetter", "*");
                            listitem.put("name", c.GetString(1));
                            listitem.put("price", c.minusPrice(shoppinglist.get(pos)));
                            listItems2.remove(0);
                            listItems2.add(0,listitem);
                            shoppinglist.remove(0);
                            shoppinglist.add(0,c);
                        }//计算总价
                        listItems2.remove(pos);
                        shoppinglist.remove(pos);
                        simpleAdapter.notifyDataSetChanged();
                    }
                }).create().show();
                return true;
            }
        });//长按移除

    }

    private void Brocast(){
        // - - - 发送热卖信息 - - - //
        Random random = new Random();
        int randomnum = random.nextInt(10); //产生一个0～(n-1)的随机数
        Intent intentBroadcast = new Intent("MyApp_Launched");
        Bundle bundle = new Bundle();
        bundle.putSerializable("goods", Data.get(randomnum));
        intentBroadcast.putExtras(bundle);
        sendBroadcast(intentBroadcast);
        // - - - end of 发送热卖信息 - - - //
    }





}
