package com.example;

import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;

public class Test_d {



    class User {

        private String name;
        private int age;

        public User(String name_, int age_) {
            name = name_;
            age = age_;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

    }

    class UserPackage {

        public User user;
        public Integer key;

        public UserPackage(User user,Integer key_) {
            this.user = user;
            key = key_;
        }
    }


    public static void main(String[] args) throws ParseException {





    }

    /**
     *
     * 已知一个 HashMap<Integer，User>集合，
     * User 有 name(String)和 age(int)属性。
     * 请写一个方法实现 对 HashMap 的排序功能，
     * 该方法接收 HashMap<Integer，User>为形参，
     * 返回类型为 HashMap<Integer，User>，
     * 要求对 HashMap 中的 User 的 age 倒序进行排序。
     * 排序时 key=value 键值对不得拆散。
     *
     */

    public void sort_a()throws ParseException{

        Map<Integer,User> mMap = new TreeMap<>();
        for (int i = 0; i < 5; i++) {
            mMap.put(i,new User("_" + i + "_name",i));
        }



    }

    public void sort(Map<Integer,User> mMap) {
        UserPackage[] mUserPackageArray = new UserPackage[mMap.size()];
        Integer[] keyArray = (Integer[])mMap.keySet().toArray();
        for (int i = 0; i < mMap.size(); i++) {
            mUserPackageArray[i] = new UserPackage(mMap.get(keyArray[i]),keyArray[i]);
        }

        UserPackage temp;
        for (int i = 0; i < mMap.size(); i++) {
            for (int j = 0; j < mMap.size() - i; j++) {
                if (mUserPackageArray[j].user.age > mUserPackageArray[j+1].user.age) {
                    temp = mUserPackageArray[j];
                    mUserPackageArray[j] = mUserPackageArray[j+1];
                    mUserPackageArray[j+1] = temp;
                }
            }
        }
        Map<Integer,User> mMap_new = new TreeMap<>();
        for (int i = 0; i < mUserPackageArray.length; i++) {
            mMap_new.put(mUserPackageArray[i].key,mUserPackageArray[i].user);
        }


    }


}
