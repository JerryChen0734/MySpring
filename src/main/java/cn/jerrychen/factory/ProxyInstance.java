package cn.jerrychen.factory;

import cn.jerrychen.emum.AspectEnum;

import java.lang.reflect.Method;

public interface ProxyInstance {
      Object newProxyInstance(Object targetObj, Object aspectObj,Method aspectMeth);
}
