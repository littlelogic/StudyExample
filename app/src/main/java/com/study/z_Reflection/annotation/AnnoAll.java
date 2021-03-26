package com.study.z_Reflection.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//--用于限制当前自定义注解类作用的对象,不指定就是可以应用于所有
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})//
/**
 * ElementType.TYPE_USE（java1.8后加入的） 和 ElementType.TYPE 冲突
 */
//@Target({ElementType.FIELD})

//@Retention(RetentionPolicy.SOURCE) //该注解类只会在源码中出现，当将源码编译成字节码的时候注解信息就被清除
//@Retention(RetentionPolicy.CLASS) //该注解类会被编译到字节码中，但是当虚拟机去加载这个字节码的时候，注解信息就被清除
@Retention(RetentionPolicy.RUNTIME) //该注解类一直保留到被加载到虚拟机中

//指定子类可以继承父类的注解，只能是类上的注解，方法和字段的注解不能继承。即如果父类上的注解是@Inherited修饰的就能被子类继承。
@Inherited

//指定被标注的注解会包含在javadoc中
@Documented

public @interface AnnoAll {
	
	int age();
	String name() default "leave";//有默认值，使用时，可以不赋值
	String[] likes() default {"唱歌","跳舞"};

}
