package panda.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Student{

    private Integer id;
    @Value("xiong")
    private String name;

    private String num;

    public void  StudentInit() {
        System.out.println("Student.StudentInit");
    }

    public void hello(){
        System.out.println("Student.hello");
    }
}
