package cn.jerrychen.factory.impl;

import cn.jerrychen.emum.AspectEnum;
import cn.jerrychen.factory.ProxyInstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class ProxyInstanceImpl   {
    public Object newProxyInstance(Object target, AspectEnum aspectEnum, Method method) {

     /*   return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object invoke = method.invoke(target, args);
                        return invoke;
                    }
                });*/

        return null;
    }
}
