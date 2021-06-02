package com.example.wujiawen.proxy;

import java.lang.reflect.Method;

public abstract class MethodInvokeDelegate {

    public Object beforeInvoke(Object target, Method method, Object[] args) {
        return null;
    }

    public Object afterInvoke(Object target, Method method, Object[] args, Object beforeInvoke, Object invokeResult) {
        if (beforeInvoke != null) {
            return beforeInvoke;
        }
        return invokeResult;
    }

}
