package com.example.wujiawen.proxy;

import com.badlogic.utils.ALog;

public class Teacher implements People {
    String name = "aaaaaaa";
    Teacher mTeacher;

    Teacher getTeacher() {
        if (mTeacher == null)
            mTeacher = new Teacher();
        return mTeacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String work() {
        ALog.i("wjw",name + "老师教书育人...");
        return "教书的是" + name + "老师";
    }

}
