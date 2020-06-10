package cn.jerrychen.emum;

import cn.jerrychen.factory.ProxyInstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//嵌套枚举类型是隐式静态的。显式声明嵌套枚举类型为静态是允许的。
public enum AspectEnum implements ProxyInstance {
    before {
        @Override
        public Object newProxyInstance(Object targetObj, Object aspectObj, Method aspectMeth) {
            return Proxy.newProxyInstance(targetObj.getClass().getClassLoader(),
                    targetObj.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            aspectMeth.invoke(aspectObj);
                            Object invoke = method.invoke(targetObj, args);
                            return invoke;
                        }
                    });
        }
    }, after {
        @Override
        public Object newProxyInstance(Object targetObj, Object aspectObj, Method aspectMeth) {
            return null;
        }
    }, around {
        @Override
        public Object newProxyInstance(Object targetObj, Object aspectObj, Method aspectMeth) {
            return null;
        }
    }


}
