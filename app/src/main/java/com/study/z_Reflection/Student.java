package com.study.z_Reflection;

import com.study.z_Reflection.annotation.AnnoAll;

@AnnoAll(age = 11)
public class Student extends Person{

    public String TAG = Student.class.getSimpleName();//类名称

    private Student(){
        System.out.println(TAG + "构造方法private-do");
    }

    private Student(float balue){
        System.out.println(TAG + "构造方法private-b-do");
    }

    //（默认的构造方法）
    Student(String str){
        System.out.println(TAG + "构造方法default-do");
    }


    protected Student(boolean score_){
        System.out.println(TAG + "构造方法protected-do");
    }

    public Student(int score_){
        System.out.println(TAG + "构造方法public-a-do");
    }

    public Student(int score_,int grade){
        System.out.println(TAG + "构造方法public-b-do");
    }

    //=====================
    @AnnoAll(age = 22)
    private int score = 0;
    public int grade = 0;
    public int final_grade = 2;

    @AnnoAll(age = 33)
    private boolean setScore(int value){
        score = value;
        if (value > 85) {
            return true;
        }
        return false;
    }

    public void setGrade(int value) {
        grade = value;
    }

    public void setGrade(Integer value) {
        grade = value;
    }

    public Void setGrade() {
        return null;
    }

    public static void setGrade(Void mVoid) {

    }

    //---------

    @Override
    public void setName(String name_){

    }

    @Override
    public void get_method_public_in_Animal(String name_){

    }



}
