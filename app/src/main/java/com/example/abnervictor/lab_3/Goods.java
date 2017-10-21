package com.example.abnervictor.lab_3;

import java.io.Serializable;

/**
 * Created by Abner on 2017/10/19.
 */

public class Goods implements Serializable{

    private String name;
    private double price;
    private String pricetag;
    private String info1;
    private String info2;
    private boolean like;
    private int PicID;

    public Goods(String name,double price, String info1, String info2, int PicID){
        this.name = name;
        this.price = price;
        this.info1 = info1;
        this.info2 = info2;
        this.PicID = PicID;
        like = false;
        pricetag = "¥ "+ String.format("%.2f",price);
    }

    public String GetString(int i){
        switch (i){
            case 1:return name;
            case 2:return pricetag;
            case 3:return info1;
            case 4:return info2;
            default:return null;
        }
    }

    public char getFirst(){
        return name.charAt(0);
    }

    public int getPicID(){ return PicID; }

    public double getPrice(){
        return price;
    }

    public String addPrice(Goods g){
        this.price+=g.getPrice();
        pricetag = "¥ "+ String.format("%.2f",price);
        return pricetag;
    }

    public String minusPrice(Goods g){
        this.price-=g.getPrice();
        pricetag = "¥ "+ String.format("%.2f",price);
        return pricetag;
    }

    public boolean getLike(){
        return like;
    }

    public void reverseLike(){
        like = !like;
    }

}
