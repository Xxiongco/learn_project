package base;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
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

/**
 *  总结：虽然后面的没有看完，但是基本上可以确定，向接口发起请求，是先获取socket,然后获取socket的outPutStream,
 *  再往里面塞入数据，然后便可以获取请求的结果。
 *
 *
 *  spring的feign基于CloseableHttpClient
 *
 *
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
     *
     *  通过JDK网络类Java.net.HttpURLConnection；
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

    /**
     *  通过common封装好的HttpClient；
     *
     *  执行
     *  httpClient.executeMethod(getMethod)
     *  的时候
     *  HttpMethodDirector.executeMethod -> executeWithRetry->this.conn.open()中创造socket
     *  HttpMethodBase.execute() ： 其中会往socket中的outputStream写入数据。
     *
     */
    @Test
    public void HttpClientTest() {
        HttpClient httpClient = new HttpClient();
        //设置Http连接超时为5秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        //2.生成GetMethod对象并设置参数
        GetMethod getMethod = new GetMethod(HTTP_URL);
        //设置get请求超时为5秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        //设置请求重试处理，用的是默认的重试处理：请求三次
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        String response = "";
        //3.执行HTTP GET 请求
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            //4.判断访问的状态码
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("请求出错：" + getMethod.getStatusLine());
            }
            //5.处理HTTP响应内容
            //HTTP响应头部信息，这里简单打印
            Header[] headers = getMethod.getResponseHeaders();
            for(Header h : headers) {
                System.out.println(h.getName() + "---------------" + h.getValue());
            }
            //读取HTTP响应内容，这里简单打印网页内容
            //读取为字节数组
            byte[] responseBody = getMethod.getResponseBody();
            response = new String(responseBody, "UTF-8");
            System.out.println("-----------response:" + response);
            //读取为InputStream，在网页内容数据量大时候推荐使用
            //InputStream response = getMethod.getResponseBodyAsStream();
        } catch (HttpException e) {
            //发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("请检查输入的URL!");
            e.printStackTrace();
        } catch (IOException e) {
            //发生网络异常
            System.out.println("发生网络异常!");
        } finally {
            //6.释放连接
            getMethod.releaseConnection();
        }
        System.out.println(response);
    }

    /**
     *  通过Apache封装好的CloseableHttpClient
     *  这个很复杂责任链模式
     *  而且，对socket的写入也很麻烦，需要走很多路径
     *
     *  spring的feign基于这个 InternalHttpClient
     */
    @Test
    public void closeableHttpClientTest() {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(HTTP_URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //返回json格式
                String res = EntityUtils.toString(response.getEntity());
                System.out.println(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
