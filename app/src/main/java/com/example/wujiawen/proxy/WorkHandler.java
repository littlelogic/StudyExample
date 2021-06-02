package com.example.wujiawen.proxy;

import android.util.Log;

import com.badlogic.utils.ALog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class WorkHandler extends MethodInvokeDelegate implements InvocationHandler {

    //代理类中的真实对象
    private Object obj;

    @Override
    public Object beforeInvoke(Object target, Method method, Object[] args) {
        ALog.i("wjw","执行了beforeInvoke.......");
        return super.beforeInvoke(target, method, args);
    }

    @Override
    public Object afterInvoke(Object target, Method method, Object[] args, Object beforeInvoke, Object invokeResult) {
        ALog.i("wjw","执行了AfterInvoke.......");
        return super.afterInvoke(target, method, args, beforeInvoke, invokeResult);
    }

    //构造函数，给我们的真实对象赋值
    public WorkHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object before = beforeInvoke(obj, method, args);
        //在真实的对象执行之前我们可以添加自己的操作
        ALog.i("wjw","before invoke。。。");
        Object invoke = method.invoke(obj, args);
        //在真实的对象执行之后我们可以添加自己的操作
        ALog.i("wjw","after invoke。。。");
        return afterInvoke(obj, method, args, before, "哈哈哈");
    }

}

