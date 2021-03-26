package com.study.dataSave.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

public class SerializableTest {

	static String path_txt = "";

	public static void main(String[] args) throws Exception {

		try {
			///new SerializableTest().showURL();
		} catch (Exception e) {
			e.printStackTrace();
		}

		File directory = new File("");// 参数为空
		String courseFile = directory.getCanonicalPath();
		courseFile = courseFile + "/aa_test/";
		File floder = new File(courseFile);
		if(!floder.exists()){
			floder.mkdir();
		}
		path_txt = floder.getAbsolutePath() + "/serializable_txt.txt";
		System.out.println("SerializableTest-main-path_txt->"+path_txt);
		File file = new File(path_txt);
		if(!file.exists()){
			file.createNewFile();
		}
		///------------------------

		Person user = new Person();
		user.setDate();
		user.setAge(23);
		user.setName("zhangsan");

		writeUser(user);
		Person user2 = readUser();
		System.out.println(user2.toString());
	}

	/*
	 * 对象的反序列化
	 */
	private static Person readUser() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path_txt)));
		Person readObject = (Person) ois.readObject();
		ois.close();
		return readObject;
	}
	/*
	 * 将User对象序列化到本地磁盘
	 */
	private static void writeUser(Person user) throws FileNotFoundException, IOException {
		
		//创建对象输出流
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path_txt)));
		//输出对象到本地
		oos.writeObject(user);
		//关闭资源
		oos.close();
		
	}

	//=======================================

	public void showURL() throws IOException {

		System.out.println("SerializableTest-showURL--------------------------->");
		// 第一种：获取类加载的根路径   D:\git\daotie\daotie\target\classes
		File f = new File(this.getClass().getResource("/").getPath());
		System.out.println(f);

		// 获取当前类的所在工程路径; 如果不加“/”  获取当前类的加载目录  D:\git\daotie\daotie\target\classes\my
		File f2 = new File(this.getClass().getResource("").getPath());
		System.out.println(f2);

		// 第二种：获取项目路径    D:\git\daotie\daotie
		File directory = new File("");// 参数为空
		String courseFile = directory.getCanonicalPath();
		System.out.println(courseFile);


		// 第三种：  file:/D:/git/daotie/daotie/target/classes/
		URL xmlpath = this.getClass().getClassLoader().getResource("");
		System.out.println(xmlpath);


		// 第四种： D:\git\daotie\daotie
		System.out.println(System.getProperty("user.dir"));
		/*
		 * 结果： C:\Documents and Settings\Administrator\workspace\projectName
		 * 获取当前工程路径
		 */

		// 第五种：  获取所有的类路径 包括jar包的路径
		System.out.println(System.getProperty("java.class.path"));

	}
}
