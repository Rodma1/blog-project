package com.chen.common.cache;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
//缓存存在时间
    long expire() default 1 * 60 * 1000;
//key值
    String name() default "";

}
