import cn.jerrychen.context.impl.ClassPathXmlApplicationContext;
import cn.jerrychen.service.UserService;
import org.junit.Test;

public class test {
    @Test
    public void getUrlTest() throws Exception {
        UserService userService =(UserService)new ClassPathXmlApplicationContext("applicationContext.xml").getBean("cn.jerrychen.service.impl.UserServiceImpl");
        userService.del(1);
    }
}
