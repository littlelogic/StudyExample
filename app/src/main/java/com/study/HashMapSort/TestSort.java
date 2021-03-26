package com.study.HashMapSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class TestSort {

	public static void main(String[] args) {
		
		HashMap<Integer, User> hashMap = new HashMap<Integer, User>();
		
		User user = new User();  android.os.Handler mddd;
		user.setName("张三");
		user.setAge(23);
		hashMap.put(1, user);

		User user2 = new User();
		user2.setName("李四");
		user2.setAge(24);
		hashMap.put(2, user2);

		User user3 = new User();
		user3.setAge(21);
		user3.setName("王五");
		hashMap.put(3, user3);
		
		System.out.println("排序前："+hashMap);
		HashMap<Integer, User>  sortedHashMap = sortHashMap(hashMap);
		System.out.println("排序后："+sortedHashMap);
	}

	private static HashMap<Integer, User> sortHashMap(HashMap<Integer, User> hashMap) {
		
		/*
		 * 创建一个有序的HashMap数据结构，LinkedHashMap
		 */
		LinkedHashMap<Integer, User> newHashMap = new LinkedHashMap<Integer,User>();
		//凡是要对集合排序了，首先想到的就是集合的工具类
		
		//把Map转换为Set集合
		Set<Entry<Integer,User>> entrySet = hashMap.entrySet();
		////把Set集合转换为List集合
		ArrayList<Entry<Integer,User>> list = new ArrayList<>(entrySet);
		//对List排序
		Collections.sort(list, new Comparator<Entry<Integer,User>>() {

			@Override
			public int compare(Entry<Integer, User> o1, Entry<Integer, User> o2) {
				//如果是倒序，就是后者的值减去前者的值
				// 返回值为int类型，大于0表示正序，小于0表示逆序
				return o2.getValue().getAge() - o1.getValue().getAge();
			}
		});
		
		//将排好顺序的List转换为LinkedHashMap
		for(int i=0;i<list.size();i++){
			Entry<Integer, User> entry = list.get(i);
			newHashMap.put(entry.getKey(), entry.getValue());
		}
		
		
		return newHashMap;
	}

	/*public static void main(String[] args ){

	}*/

}
