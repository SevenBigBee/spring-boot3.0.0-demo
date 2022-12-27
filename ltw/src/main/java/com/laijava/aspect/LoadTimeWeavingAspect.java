package com.laijava.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @ClassName LoadTimeWeavingAspect
 * @Description LTW切面
 * @Date 2022/12/6 16:37
 * @Version 1.0
 * @Author 3045566537@qq.com
 **/

@Aspect
@Component
public class LoadTimeWeavingAspect {

//     解决找不到aspectOf()方法异常
    public static LoadTimeWeavingAspect aspectOf() {
        return new LoadTimeWeavingAspect();
    }



    //通过切面实现noise重写
    @Around("execution(public * com.laijava..*.*(..))")
    public Object invoked(ProceedingJoinPoint pjp) throws Throwable {
        return "dog dog dog";
    }
}
