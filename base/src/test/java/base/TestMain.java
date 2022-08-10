package base;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestMain {

    @Test
    public void test() {
        String ZB_ROOM_REGX = "o2pcm:zb:%s&%s:room";

        String format = String.format(ZB_ROOM_REGX, "nihao", "a ");
        System.out.println(format);

    }

    @Test
    public void testJvisualVm() throws InterruptedException {

        List<Object> objectList = new ArrayList<>();


        while (true) {
            objectList.add(new Object());
            Thread.sleep(1000);
        }

    }


    public static void main(String[] args) throws InterruptedException {

        new TestMain().testJvisualVm();
    }

}
