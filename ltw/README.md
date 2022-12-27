#  ltw
spring boot Load-time waving示例，通过ltw实现对一个未在spring容器中的对象进行切面操作

## Notice
1. spring boot 3.0.0需要jdk11+,建议使用官方推荐的jdk11避免代码兼容问题,依据工程依赖默认会引入spring-instrument-6.0.2.jar与aspectjweaver-1.9.9.1.jar，经过验证jdk与aspectjweaver存在版本兼容问题，
2. Load-time Waving是基于JVMTI实现的一种技术，工程运行时候需要指定VM
   1. -javaagent:your_maven_repo_patch\org\springframework\spring-instrument\6.0.2\spring-instrument-6.0.2.jar
   2. -javaagent:your_maven_repo_patch\repository\org\aspectj\aspectjweaver\1.9.9.1\aspectjweaver-1.9.9.1.jar


