package com.example.abnervictor.lab_3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView shoppingList;
    private RecyclerView mRecyclerView;
    private AlertDialog.Builder builder;
    private SimpleAdapter simpleAdapter;
    private CommonAdapter<Map<String,Object>> commonAdapter;
    private FloatingActionButton fab;
    private List<Goods> data = new ArrayList<>();
    private List<Goods> shoppinglist = new ArrayList<>();
    private List<Map<String,Object>> listItems1 = new ArrayList<>();
    private List<Map<String,Object>> listItems2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        InitData();
        setListner();
    }

    private void findView(){
        setContentView(R.layout.activity_main);
        shoppingList = (ListView) findViewById(R.id.shopping_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.goods_list);
        builder = new AlertDialog.Builder(this);
        fab = (FloatingActionButton) findViewById(R.id.floating_button);
    }

    private void InitData(){
        data.add(new Goods("Enchated Forest",5.00,"作者","Johanna Basford",R.drawable.EnchatedForest));
        data.add(new Goods("Arla Milk",59.00,"产地","德国",R.drawable.Arla));
        data.add(new Goods("Devondale Milk",79.00,"产地","澳大利亚",R.drawable.Devondale));
        data.add(new Goods("Kindle Oasis",2399.00,"容量","8GB",R.drawable.kindle));
        data.add(new Goods("waitrose 早餐麦片",179.00,"重量","2kg",R.drawable.waitrose));
        data.add(new Goods("Mcvitie's 饼干",14.90,"产地","英国",R.drawable.Mcvitie));
        data.add(new Goods("Ferrero Rocher",132.59,"重量","300g",R.drawable.Ferrero));
        data.add(new Goods("Maltesers",141.43,"重量","118g",R.drawable.Maltesers));
        data.add(new Goods("Lindt",139.42,"重量","249g",R.drawable.Lindt));
        data.add(new Goods("Borggreve",28.90,"重量","640g",R.drawable.Borggreve));

        {
            Map<String,Object> listitem = new LinkedHashMap<>();
            listitem.put("firstLetter","*");
            listitem.put("name","购物车");
            listitem.put("price","价格");
            listItems2.add(listitem);
        }//为购物车添加首行

        for (Goods c : data){
            Map<String,Object> listitem = new LinkedHashMap<>();
            listitem.put("name",c.GetString(1));
            listitem.put("firstLetter",c.getFirst());
            listItems1.add(listitem);
        }//填充列表
        // - - - ListView - - - //
        simpleAdapter = new SimpleAdapter(this,listItems2);
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
                Intent intent = new Intent(MainActivity.this,GoodsInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", data.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }

            @Override
            public void onLongClick(int position) {
                commonAdapter.removeItem(position);
            }
        });

        // - - - RecyclerView - - - //
    }

    private void setListner(){

    }





}
