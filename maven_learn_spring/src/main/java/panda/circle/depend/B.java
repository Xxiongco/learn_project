package panda.circle.depend;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class B {

    @Autowired
    private A a;

    public void hello(){
        System.out.println("BBBBBBBB " + this);
    }
}
