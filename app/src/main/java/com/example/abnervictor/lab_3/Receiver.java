package com.example.abnervictor.lab_3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by abnervictor on 2017/10/26.
 */

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){

        if(intent.getAction().equals("Goods_AddtoCart")){
            Bundle bundle = intent.getExtras();
            Goods goods = (Goods) bundle.get("goods");
            int i = (int) bundle.get("Num");
            //
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),goods.getPicID());

            //添加notification
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("马上下单")
                    .setContentText(goods.GetString(1)+"已经添加到购物车")
                    .setTicker("商品已添加到购物车，单击查看")
                    .setLargeIcon(bmp)
                    .setSmallIcon(goods.getPicID())
                    .setAutoCancel(true);

            Intent intent1 = new Intent(context,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification notify = builder.build();
            manager.notify(i,notify);
            EventBus.getDefault().post(new MessageEvent(true,null));

        }//接收到添加购物车的广播，该广播动态注册
        else if (intent.getAction().equals("MyApp_Launched")) {
            Bundle bundle = intent.getExtras();
            Goods goods = (Goods) bundle.get("goods");
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),goods.getPicID());

            //添加notification
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("有一件商品正在热卖！")
                    .setContentText(goods.GetString(1)+"仅售"+ goods.GetString(2)+"！")
                    .setTicker("您有一条新消息")
                    .setLargeIcon(bmp)
                    .setSmallIcon(goods.getPicID())
                    .setAutoCancel(true);

            Intent intent1 = new Intent(context,GoodsInfo.class);
            intent1.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            //获取通知栏状态管理
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(0,notify);

            //EventBus.getDefault().post(new MessageEvent(false,true,goods));

        }//接收到应用启动的广播，该广播静态注册
    }
}