package base;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 *  java发起网络请求的方式
 *  1、通过JDK网络类Java.net.HttpURLConnection；
 *  2、通过common封装好的HttpClient；
 *  3、通过Apache封装好的CloseableHttpClient；
 *  4、通过SpringBoot-RestTemplate；
 */

public class NetRequestTest {

    public static String HTTP_URL = "http://localhost:8081/hello";

    /**
     *  搭建socket的目的是为了测试怎么通过socket请求接口
     */
    @Test
    public void socketTest() {
        try {
            // 和服务器创建连接
            Socket socket = new Socket("localhost", 8081);
            OutputStream outputStream = socket.getOutputStream();

            StringBuffer sb = new StringBuffer("GET /hello?message=nihao HTTP/1.1\r\n");
            // 以下为请求头
            sb.append("Host: localhost:8081 \r\n");
            sb.append("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0\r\n");
            sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
            sb.append("Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            // 注意这里不要使用压缩 否则返回乱码
            sb.append("Accept-Encoding: \r\n");
            sb.append("Connection: keep-alive\r\n");
            sb.append("Upgrade-Insecure-Requests: 1\r\n");
            // 注意这里要换行结束请求头
            sb.append("\r\n");

            outputStream.write(sb.toString().getBytes());

            outputStream.flush();

            // 从服务器接收的信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器返回信息：" + info);
            }
            br.close();
            is.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  connection.connect()的时候，会给HttpClient（继承NetworkClient，有Socket对象）赋值；
     *  connection.getResponseCode()的时候，会将相关信息写入到，socket的outputStream
     */
    @Test
    public void httpURLConnectionTest() {
        //链接
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuffer result = new StringBuffer();
        try {
            //创建连接
            URL url = new URL(HTTP_URL);
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置连接超时时间
            connection.setReadTimeout(15000);
            //开始连接
            connection.connect();

            int responseCode = connection.getResponseCode();
            //获取响应数据
            if (connection.getResponseCode() == 200) {
                //获取返回的数据
                is = connection.getInputStream();
                if (null != is) {
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String temp = null;
                    while (null != (temp = br.readLine())) {
                        result.append(temp);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭远程连接
            connection.disconnect();
            System.out.println(result);
        }
    }

}
