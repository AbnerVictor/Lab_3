package com.example.abnervictor.lab_3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by abnervictor on 2017/10/26.
 */

public class DynamicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals("Goods_AddtoCart")){
            Bundle bundle = intent.getExtras();
            Goods goods = (Goods) bundle.get("goods");
            int i = (int) bundle.get("Num");
            //

        }//接收到添加购物车的广播
    }
}