package com.study.dataSave.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class Shoes implements Parcelable{

    private String color = "init - black";

    public Shoes(String color_){
        color = color_;
    }

    @Override
    public String toString() {
        return "Shoes [color=" + color +"]";
    }


    //==========================================

    // 内容描述，用不到默认返回0即可
    @Override
    public int describeContents() {
        return 0;
    }

    // 用于对象的序列化
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color);
    }
    //用于反序列化
    public static final Parcelable.Creator<Shoes> CREATOR = new Parcelable.Creator<Shoes>() {

        //用于反序列化的核心方法
        public Shoes createFromParcel(Parcel in) {
            Shoes user = new Shoes("");
            user.color = in.readString();
            return user;
        }

        //用于反序列化数组使用
        public Shoes[] newArray(int size) {
            return new Shoes[size];
        }
    };
}
