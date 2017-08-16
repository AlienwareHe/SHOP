import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Hexun on 2017/8/5 0005.
 */
public class UnitBase {
    private String springXmlPath;
    private ClassPathXmlApplicationContext context;
    public UnitBase(){}
    public UnitBase(String springXmlPath){
        this.springXmlPath=springXmlPath;
    }
    @Before
    public void before(){
        //不对springXmlPath进行非空判断了
        context=new ClassPathXmlApplicationContext(springXmlPath);
        context.start();
    }

    @After
    public void after(){
        context.destroy();
    }

    public <T extends Object> T getBean(String beanId){
        return (T)context.getBean(beanId);

    }
    public <T extends Object> T getBean(Class<T> clz){
        return (T)context.getBean(clz);

    }
}
