package com.dify.javaclient.http;

/**
 * @author Ziyao_Zhu
 */
public class DifyRoute {
    public String method;
    public  String url;

    public DifyRoute(String method, String url) {
        this.method = method;
        this.url = url;
    }
}