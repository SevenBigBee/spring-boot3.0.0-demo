package com.laijava;

import com.alibaba.fastjson2.JSON;
import com.laijava.bo.SoapRequestBO;
import com.laijava.bo.SoapRequestBodyBO;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        SoapRequestBO soapRequestBO = new SoapRequestBO();
        String header =  "header content";
        SoapRequestBodyBO soapRequestBodyBO = new SoapRequestBodyBO();
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("name","用户名");
        jsonMap.put("age", "22");
        soapRequestBodyBO.setParamJson(JSON.toJSONString(jsonMap));
        soapRequestBO.setHeader(header);
        soapRequestBO.setBody(soapRequestBodyBO);
        System.out.println(JaxbXmlUtil.convertToXml(soapRequestBO));
    }
}