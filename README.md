# 手撸Spring
为了更好的了解spring框架，在自习时间决定手撸一个简易的spring框架。spring的三大原理则是IOC,DI,AOP，所以了解了这三个基本就了解了spring框架执行原理。
##IOC（“别找我们，我们找你”）
###IOC的介绍
Ioc—Inversion of Control，即“控制反转”，不是什么技术，而是一种设计思想。这个思想就是通过spring容器接管了程序外部资源（对象、文件……）的获取，在原来，资源的获取是需要程序员来控制（new）的，引入这一思想后，控制权反转到容器手中，由容器帮我们查找及注入依赖（DI）对象。引入这一思想后它能指导我们如何设计出松耦合、更优良的程序。
###IOC的实现步骤
第一步）配置需要扫描的包，自定义注解  
第二布）编写ClassPathXmlApplicationContext类来进行初始化  
第三步）获取配置文件中需要扫描的包路径  
第四步）将加了注解的类实例化放入map中统一管理  
第五步）将依赖注入的bean注入进实例化。
##DI（“谁依赖谁，为什么需要依赖，谁注入谁，注入了什么”）
###DI的介绍
IoC的一个重点是在系统运行中，动态的向某个对象提供它所需要的其他对象。这一点是通过DI（Dependency Injection，依赖注入）来实现的。
* 谁依赖于谁：当然是应用程序依赖于IoC容器； 
 
* 为什么需要依赖：应用程序需要IoC容器来提供对象需要的外部资源；  

* 谁注入谁：很明显是IoC容器注入应用程序某个对象，应用程序依赖的对象；  

* 注入了什么：就是注入某个对象所需要的外部资源（包括对象、资源、常量数据）。  
##AOP（“钢铁侠穿上了他的战衣”）
  史塔克是一个花花公子，每天的工作内容就是吃饭睡觉泡妹子，直到有一天他被绑架了，这个天才一气之下决定维护世界和平，每天的工作内容就会变成吃饭睡觉泡妹子打坏人，现在有两种方案：  
方案一）屌丝靠变异，把自己改造成绿巨人，但看上去这不是一个很好的选择，因为史塔克是一个花花公子，需要帅气的脸去撩妹（整洁性），而且指不定变异后基因会发生什么改变（污染代码）。  
方案二）研发一套帅气的战衣，平时继续吃饭睡觉，当需要打坏人的时候就穿上他的战衣（耦合度），还能避免变异后的坏处。  

相信大家应该知道史塔克该怎么选了吧。这种运行时，动态地将代码（战衣）切入到类的指定方法、指定位置（钢铁侠）上的编程思想就是面向切面（AOP）的编程。
##枚举策略模式
今天老师上课提了一嘴枚举策略模式，恰好我写AOP的三种增强时间（before、after、around）的时候写if else写的有点烦，于是决定秀下操作，就把if else改成了枚举策略模式。
  
###改造之前
    if(AspectEnum.before){
           //省略方法
    }else if(AspectEnum.after){
           //省略方法
    } //省略其他选项
###改造之后
    public enum AspectEnum implements ProxyInstance {
        before {
            //省略接口方法
        }, after {
            //省略接口方法
        }, around {
           //省略接口方法
        }
###调用
      //通过枚举策略模式实现jdk动态代理接口返回一个代理对象
      Object proxyObj = AspectEnum.before.newProxyInstance(target, obj, method);
