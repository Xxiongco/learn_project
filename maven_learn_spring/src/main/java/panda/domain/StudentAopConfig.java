package panda.domain;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class StudentAopConfig {

    @Pointcut("execution(public * panda.domain.*.hello(..))")
    public void myMethod(){};

    @Around("myMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        System.out.println("-----------------before-------------------");
        Object result = point.proceed();
        System.out.println("-----------------after-------------------");
        return result;
    }


}
