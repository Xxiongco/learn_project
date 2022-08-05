package panda.circle.depend;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *  C中有D
 *  D中无C
 *  用于知道什么时候出来的
 *
 */
@Component
@Getter
@Setter
public class C {
    @Autowired
    private D d;
}
