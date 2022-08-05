package panda;


import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import panda.circle.depend.A;
import panda.circle.depend.B;
import panda.circle.depend.C;
import panda.circle.depend.D;
import panda.domain.Student;


public class LearnSpringTestMain {

    /**
     *  用于走一遍基础流程，同时看看相关的参数在什么时候设置的。
     *  但是有一个地方没有理解，就是什么时候放入到二级缓存中的
     */
    @Test
    public void testBase() {

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-base.xml");

        Student student = context.getBean("student", Student.class);

        System.out.println(student);

    }
    @Test
    public void testAop() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-aop.xml");

        Student student = context.getBean("student", Student.class);
        System.out.println(student);
        student.hello();
    }

    @Test
    public void testCircle() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");

        A a = context.getBean("a", A.class);
        a.hello();

        B b = context.getBean("b", B.class);
        C c = context.getBean("c", C.class);
        D d = context.getBean("d", D.class);

        System.out.println(a);
        a.hello();

        System.out.println(b);
        b.hello();

        System.out.println(c);

        System.out.println(d);


//        Student student = context.getBean("student", Student.class);
//        System.out.println(student);


/**
 Object myFactoryBean = context.getBean("myFactoryBean");
 System.out.println(myFactoryBean.getClass());
 Object myFactoryBeanFactory = context.getBean("&myFactoryBean");
 System.out.println(myFactoryBeanFactory.getClass());
 **/

    }
}
