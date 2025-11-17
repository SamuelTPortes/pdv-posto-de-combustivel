package com.br.pdvpostocombustivelfrontend.frontend.test;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.util.HttpClient;

public class SmokeCaller {
    public static void main(String[] args) throws Exception {
        System.out.println("GET pessoas:");
        try { System.out.println(HttpClient.get(AppConfig.API_BASE_URL + "/pessoas?page=0&size=5")); } catch (Exception e) { System.out.println("ERR: " + e.getMessage()); }

        System.out.println("GET produtos:");
        try { System.out.println(HttpClient.get(AppConfig.API_BASE_URL + "/produtos?page=0&size=5")); } catch (Exception e) { System.out.println("ERR: " + e.getMessage()); }

        System.out.println("POST login:");
        try { System.out.println(HttpClient.post(AppConfig.LOGIN_ENDPOINT, "{\"usuario\":\"admin\",\"senha\":\"admin123\"}")); } catch (Exception e) { System.out.println("ERR: " + e.getMessage()); }
    }
}

