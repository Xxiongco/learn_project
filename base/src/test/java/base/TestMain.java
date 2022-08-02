package base;

import org.junit.jupiter.api.Test;

public class TestMain {

    @Test
    public void test() {
        String ZB_ROOM_REGX = "o2pcm:zb:%s&%s:room";

        String format = String.format(ZB_ROOM_REGX, "nihao", "a ");
        System.out.println(format);

    }
}
