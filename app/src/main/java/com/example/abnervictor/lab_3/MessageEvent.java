package com.example.abnervictor.lab_3;

/**
 * Created by Abner on 2017/10/26.
 */

public class MessageEvent {
    private boolean Jump_to_Cart;
    private boolean Refresh_GoodsInfo;
    private Goods goods;
    public MessageEvent(boolean Cart, Goods goods_){
        Jump_to_Cart = Cart;
        goods = goods_;
        Refresh_GoodsInfo = false;
    }
    public boolean JumptoCart(){
        return Jump_to_Cart;
    }
    public Goods getGoods(){
        return goods;
    }
    public void RefreshGoodsInfo(){
        Refresh_GoodsInfo = true;
    }
    public boolean getRefresh_GoodsInfo(){
        return Refresh_GoodsInfo;
    }
}
