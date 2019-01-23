package com.atguigu.gmall.password.web.test;

import com.atguigu.gmall.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

public class TestJwt {

    public static void main(String[] args){

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJuaWNrTmFtZSI6ImNoZXJyeSIsInVzZXJJZCI6InJvb3QifQ.IYAfAz8aZFydEhgfweR4KxLQlsWWPMrVHYGHrky2auc";
        Map mapdecode = JwtUtil.decode("2312019",token, "2020-01-24");
        System.out.println(mapdecode);
//          creatToken();
    }

    public static void creatToken(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("userId","root");
        hashMap.put("nickName","cherry");
        String encode = "2019-01-23";
       // System.out.println(encode);
        String token = JwtUtil.encode("2312019", hashMap, encode);
        System.out.println(token);
    }
}
