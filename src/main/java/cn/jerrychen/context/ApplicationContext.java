package cn.jerrychen.context;

public interface ApplicationContext {
    public Object getBean(String beanName);

    public <T> T getBean(Class<T> c);
}
