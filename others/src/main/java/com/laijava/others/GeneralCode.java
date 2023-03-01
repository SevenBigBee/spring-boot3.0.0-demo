package com.laijava.others;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 根据规则替换遍历出字符串中的-，=,？三种字符
 */
public class GeneralCode {

    public static void main(String args[]){
        Map<String, List<String>> keyMap = new HashMap<>();
        List<String> nums = Stream.of("0,1,2,3,4,5,6,7,8,9".split(",")).collect(Collectors.toList());
        keyMap.put("-", nums);
        List<String> code = Stream.of("a,b,c,d,e,f".split(",")).collect(Collectors.toList());
        keyMap.put("=", code);
        List<String> codeAndNums = Stream.of("a,b,c,d,e,f,0,1,2,3,4,5,6,7,8,9".split(",")).collect(Collectors.toList());
        keyMap.put("？", codeAndNums);

        List<String> encodeStrList = new ArrayList<>();
        encodeStrList.add("6b5-c643ed0=9dcf");

        for (String encodeStr :encodeStrList){
            System.out.println("======="+encodeStr +"start" + "=======" );
            replaceCode(keyMap, encodeStr);
            System.out.println("======="+encodeStr +"end" + "=======" );
        }
    }

    private static void replaceCode(Map<String, List<String>> keyMap, String encodeStr) {
        for (Map.Entry<String, List<String>> entries :  keyMap.entrySet()){
            // 如果包含加密字符
            if (encodeStr.contains(entries.getKey())){
                List<String> values = entries.getValue();
                for (String val : values){
                    String s = encodeStr.replaceFirst(entries.getKey(), val);
                    // 判断是否还包含加密字符，包含则继续解析，不包含则打印出来
                    if (containsKey(s, keyMap)){
                        replaceCode(keyMap, s);
                    } else{
                        System.out.println(s);
                    }
                }
            }
        }
    }

    private static boolean containsKey(String s, Map<String, List<String>> keyMap) {
        for (Map.Entry<String, List<String>> entries :  keyMap.entrySet()){
            if (s.contains(entries.getKey())){
                return true;
            }
        }

        return false;
    }
}
