package cn.jerrychen.aspect;

import cn.jerrychen.annotation.Aspect;
import cn.jerrychen.annotation.Before;
import cn.jerrychen.annotation.Service;

@Service
@Aspect
public class MyAspect {
    //前置增强方法
    @Before("cn.jerrychen.service.impl.UserServiceImpl.del()")
    public void before() {
        System.out.println("前置代码增强.....");
    }

}
