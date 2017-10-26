package com.example.abnervictor.lab_3;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * Created by abnervictor on 2017/10/26.
 */

public class StaticReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        if (intent.getAction().equals("MyApp_Launched")) {
            Bundle bundle = intent.getExtras();
            Goods goods = (Goods) bundle.get("goods");

            Resources res = context.getResources();
            Bitmap    bmp = BitmapFactory.decodeResource(res,goods.getPicID());


            //添加notification
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("有一件商品正在热卖！")
                   .setContentText(goods.GetString(0)+"仅售 "+goods.GetString(1))
                   .setTicker("您有一条新消息")
                   .setLargeIcon(bmp)
                   .setSmallIcon(goods.getPicID())
                   .setAutoCancel(true);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification notify = builder.build();
            manager.notify(0,notify);

        }//接收到应用启动的广播
    }
}
