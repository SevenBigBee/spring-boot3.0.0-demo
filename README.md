#  spring-boot3.0.0-demo
基于spring boot 3.0.0 实践demo

## Notice
1. spring boot 3.0.0需要jdk11+,建议使用官方推荐的jdk11 避免代码兼容问题
2. 关于unnamed module异常，jvm中需要添加下述参数
   1. --add-opens java.base/java.lang=ALL-UNNAMED
   2. --add-opens java.base/java.lang.reflect=ALL-UNNAMED

## Module
### ltw
spring boot Load-time waving示例，通过ltw实现对一个未在spring容器中的对象进行切面操作