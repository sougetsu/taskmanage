package com.sdmx.framework.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD}) 
@Documented
public @interface BussAnnotation {  
    //模块名  
    String moduleName();  
    //操作内容  
    String option();  
}
