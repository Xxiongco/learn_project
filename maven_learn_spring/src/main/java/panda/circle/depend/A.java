package panda.circle.depend;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  A中有B
 *  B中有A
 */

@Getter
@Setter
@Component
public class A {

    @Autowired
    private B b;

    public void hello(){
        System.out.println("AAAAAAAA " + this);
    }
}
