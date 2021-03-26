package com.study.z_reflection_view;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewUtils {

	public static void inject(Activity activity) {
		//绑定控件
		try {
			bindView(activity);
			bindOnClick(activity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void bindView(Activity activity) throws IllegalAccessException, IllegalArgumentException {
		/*
		 * 1. 获取Activity的字节码
		 */
		Class clazz = activity.getClass();
		/*
		 * 2. 获取到该字节码中的所有的Filed
		 */
		Field[] declaredFields = clazz.getDeclaredFields();
		/*
		 * 3. 遍历字段，判断哪些是我们想要的字段（只有添加了ViewInject注解的字段）
		 */
		for(Field field : declaredFields){
			//获取字段上面的注解
			ViewInject viewInject = field.getAnnotation(ViewInject.class);
			if (viewInject!=null) {
				/*
				 * 4. 获取当前注解的值
				 */
				int resId = viewInject.value();
				/*
				 * 5.通过调用Activity的findViewById方法，获取当前id为resId的控件
				 */
				View view = activity.findViewById(resId);
				/*
				 * 6. 将当前的view设置给当前的Filed
				 */
				field.setAccessible(true);
				//给Activity对象的filed字段设置值为view对象
				field.set(activity, view);
				
			}else{
				//TODO do nothing 
			}
			
		}
		
	}
	
	private static void bindOnClick(final Activity activity) {
		/*
		 * 1. 获取字节码
		 */
		Class clazz = activity.getClass();
		/*
		 * 2. 获取所有的方法
		 */
		Method[] declaredMethods = clazz.getDeclaredMethods();
		/*
		 * 3. 遍历方法，找出方法上声明了OnClick注解的方法
		 */
		for(final Method method : declaredMethods){
			/*
			 * 4. 获取当前方法上的注解
			 */
			OnClick onClick = method.getAnnotation(OnClick.class);
			
			if (onClick!=null) {
				/*
				 * 5. 获取注解中的值
				 */
				int resId = onClick.value();
				/*
				 * 6. 获取到id为resId的View
				 */
				final View view = activity.findViewById(resId);
				/*
				 * 7. 给当前的View绑定点击监听事件
				 */
				view.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						/*
						 * 8. 通过反射调用当前的用户方法
						 */
						method.setAccessible(true);//必须暴力反射
						
						try {
							method.invoke(activity, view);
						} catch (Exception e) {
							e.printStackTrace();
						}//?为什么不让抛异常
						
					}
				});
				
			}
			
		}
	}

}
