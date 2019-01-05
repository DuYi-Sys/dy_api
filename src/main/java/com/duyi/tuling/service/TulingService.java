package com.duyi.tuling.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TulingService {

    /**
     *
     * @param msg 发送的消息
     * @return
     * @throws IOException
     */
    public String createConnection(String msg) throws IOException {
        HttpClient client = new HttpClient();
        client.getHostConfiguration().setHost( "openapi.tuling123.com",80,"http");
        HttpMethod method = getPostMethod(msg);    // 使用 POST 方式提交数据
        client.executeMethod(method);   //打印服务器返回的状态
//        System.out.println(method.getStatusLine());   //打印结果页面
        String response  =new String(method.getResponseBodyAsString().getBytes("8859_1"));
        //打印返回的信息
//        System.out.println(response);
        method.releaseConnection();
        return response;

    }

    public HttpMethod getPostMethod(String text) throws IOException {
        String data = "{\"reqType\":0,\"perception\": {\"inputText\": {\"text\": '"+ text + "'}},\"userInfo\": {\"apiKey\": \"7381b1f8c75e4307b454e3f531d5c5cf\" ,\"userId\": \"123456\"}}";
        PostMethod post =  new PostMethod("/openapi/api/v2");
        post.setRequestHeader("Content-Type", "application/json;charset=gbk");
        post.setRequestHeader("Host","openapi.tuling123.com");
//        post.setRequestHeader("content-length",data.length() + "");
//        post.setRequestHeader("Connetcion","close");
        NameValuePair nameValuePair = new NameValuePair();
        nameValuePair.setValue(data);
        post.setRequestBody(data);
//        post.setRequestBody(new NameValuePair[] {nameValuePair});
        return post;
    }
}
