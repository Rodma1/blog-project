package com.chen.common.aop;

import java.lang.annotation.*;

/**
 * 日志注解
 */
//type代表可以放在累上面   method：代表可以放在方法上
@Target(ElementType.METHOD)//我们这里放在方法上，所以用method参数就够了
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
//模块名称
    String module() default "";
// 操作名称
    String operation() default "";
}