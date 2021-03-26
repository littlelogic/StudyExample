package com.study.z_Reflection;

public class Person extends Animal{

    public String TAG = Student.class.getSimpleName();//类名称
    private int age = 0 ;
    public String name = "";


    private Person(String name_){
        System.out.println(TAG + "构造方法privatedo");
    }

    Person(int age_,String name_){
        System.out.println(TAG + "构造方法default-do");
    }

    protected Person(int age_){
        System.out.println(TAG + "构造方法protected-do");
    }

    public Person(){

    }

    public Person(boolean mark){

    }



    //==============================

    private void setAge(int value){
        age = value;
    }

    public void setName(String name_){
        name = name_;
    }

    public final void setTall(String name_){
        name = name_;
    }

    void setEyeColor(String name_){

    }

    protected void sethairColor(String name_){

    }

    @Override
    public void get_method_public_in_Animal(String name_){

    }


}
