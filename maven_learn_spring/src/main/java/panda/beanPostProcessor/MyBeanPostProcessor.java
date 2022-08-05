package panda.beanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


public class MyBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        System.out.println("MyBeanPostProcessor.postProcessBeforeInitialization");

        return bean;
    }


    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        
        System.out.println("MyBeanPostProcessor.postProcessAfterInitialization");
        return bean;
    }

}
