package cn.jerrychen.context.impl;

import cn.jerrychen.annotation.*;
import cn.jerrychen.context.ApplicationContext;
import cn.jerrychen.emum.AspectEnum;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClassPathXmlApplicationContext implements ApplicationContext {
    private static Set<String> classes = new HashSet<>();

    private static Map<String, Object> objMap = new HashMap<>();

    private static boolean isAopAutoProxy = false;

    private static String classesUrl;


    public ClassPathXmlApplicationContext(String fileName) throws Exception {
        //获得项目编译路径 url.getPath()--->C://user/work/+cn/jerrychen
        URL url = ClassPathXmlApplicationContext.class.getClassLoader().getResource("");
        classesUrl = url.getPath().substring(0, url.getPath().lastIndexOf("target")) + "target/classes/";
        String scanPackageName = getScanPackage(fileName);
        String path = classesUrl + scanPackageName.replace(".", "/");
        //扫描所有包
        doScan(path, classesUrl);
        //获得所有加了注解的value值,并实例化加入objMap进行统一管理
        for (String c : classes) {
            Class<?> aClass = Class.forName(c);
            if (aClass.isAnnotationPresent(Service.class)) {
                String value = aClass.getAnnotation(Service.class).value();
                objMap.put(aClass.getName(), aClass.newInstance());
            } else if (aClass.isAnnotationPresent(Repository.class)) {
                String value = aClass.getAnnotation(Repository.class).value();
                objMap.put(aClass.getName(), aClass.newInstance());
            }
        }
        //自动注入
        for (Object obj : objMap.values()) {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field f : declaredFields) {
                if (f.isAnnotationPresent(Autowired.class)) {
                    f.setAccessible(true);
                    f.set(obj, getBean(f.getType()));
                }
            }

            //AOP动态代理
            if (isAopAutoProxy) {
                //判断是否类是否被Aspect注解
                if (obj.getClass().isAnnotationPresent(Aspect.class)) {
                    Method[] methods = obj.getClass().getDeclaredMethods();
                    for (Method method : methods) {
                        //判断方法是否被before注解
                        if (method.isAnnotationPresent(Before.class)) {
                            String targetName = method.getDeclaredAnnotation(Before.class).value();
                            targetName = targetName.substring(0, targetName.lastIndexOf("."));
                            Object target = getBean(targetName);
                            //通过枚举策略模式实现jdk动态代理接口返回一个代理对象
                            Object proxyObj = AspectEnum.before.newProxyInstance(target, obj, method);
                            //将增强后的方法覆盖原方法
                            objMap.put(targetName,proxyObj);
                        }
                    }

                }

            }

        }


    }

    private void doScan(String path, String projRootPath) {
        //根据包绝对路径获取包下面的所有文件,先得到File对象
        File file = new File(path);
        //得到file下面的所有文件或文件夹
        File[] files = file.listFiles();
        for (File f : files) {
            //如果是文件夹，递归往下继续取
            if (f.isDirectory()) {
                doScan(f.getAbsolutePath(), projRootPath);
            } else {//如果是文件.class
                //cn.jerrychen.service.impl.UserServiceImpl
                //将项目编译根路径/替换成\\,并且去掉第一个盘符/
                String newRootPath = projRootPath.substring(1).replace("/", "\\");
                String filePath = f.getPath();//C:\workspaces\workspace\spring-heima\target\classes\cn\jerrychen\context\ApplicationContext.class
                //获取根路径在文件路径下的下标
                String classPath = filePath.replace(newRootPath, "").replace(".class", "").replace("\\", ".");
                classes.add(classPath);
            }
        }
    }

    //解析XML，获取包扫描路径 cn.jerrychen
    private String getScanPackage(String fileName) {
        //通过文档构建器工厂获得文档构建器
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();

            //通过文档构建器获得文档对象
            String xmlPath = classesUrl + fileName;
            Document document = documentBuilder.parse(xmlPath);
            //通过文档对象获得根标签
            Element rootElement = document.getDocumentElement();
            if (rootElement == null) {
                return null;
            }
            //通过rootElement标签获得子标签
            NodeList nodeList = rootElement.getElementsByTagName("component-scan");
            isAopAutoProxy = rootElement.getElementsByTagName("aspectj-autoproxy") != null;
            if (nodeList == null) {
                return null;
            }
            return nodeList.item(0).getAttributes().item(0).getNodeValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Object getBean(String beanName) {
        return objMap.get(beanName);
    }

    @Override
    public <T> T getBean(Class<T> c) {
        for (Object value : objMap.values()) {
            Class<?>[] interfaces = value.getClass().getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if (anInterface == c) return (T) value;
            }
        }
        return null;
    }
}
