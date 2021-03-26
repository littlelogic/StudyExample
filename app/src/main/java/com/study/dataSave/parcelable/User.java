package com.study.dataSave.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

	private String name;
	private int age;
	private Shoes mShoes = new Shoes("User set white");
	private Shoes nullShoes;
	private Shoes parcelableShoes = new Shoes("User set white b");
	private Shoes mShoesArray[] = new Shoes[2];
	private String like[] = {"sing","dance"};

	public void writeData(){
		mShoesArray[0] = null;
		mShoesArray[1] = new Shoes("User structuror set white");
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "\n"+"，mShoes=" + mShoes.toString() +",nullShoes="+nullShoes+"]"
				+ "\n"+  ", parcelableShoes=" + parcelableShoes
				+ "\n"+  ", like[0]=" + like[0]
				+ "\n"+  ", like[1]=" + like[1]
		;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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
		// 将name和age属性序列化出去
		dest.writeString(name);
		///dest.writeInt(age);

		if (nullShoes == null) {
			dest.writeInt(0);
		} else {
			dest.writeInt(1);
			mShoes.writeToParcel(dest,flags);
		}
		if (mShoes == null) {
			dest.writeInt(0);
		} else {
			dest.writeInt(1);
			mShoes.writeToParcel(dest,flags);
		}
		//----
		dest.writeParcelable(parcelableShoes,flags);
		dest.writeStringArray(like);

	}
	//用于反序列化
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

		//用于反序列化的核心方法
		public User createFromParcel(Parcel in) {
			User user = new User();
			user.setName(in.readString());
			///user.setAge(in.readInt());

			if (in.readInt() == 0) {
				user.nullShoes = null;
			} else {
				user.nullShoes = Shoes.CREATOR.createFromParcel(in);
			}
			if (in.readInt() == 0) {
				user.mShoes = null;
			} else {
				user.mShoes = Shoes.CREATOR.createFromParcel(in);
			}
			user.parcelableShoes = in.readParcelable(Shoes.class.getClassLoader());
			in.readStringArray(user.like);
			return user;
		}

		//用于反序列化数组使用
		public User[] newArray(int size) {
			return new User[size];
		}
	};

}
