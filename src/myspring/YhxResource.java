package myspring;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//注解处理在运行时刻
@Retention(RetentionPolicy.RUNTIME)
//对字段和方法使用注解
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface YhxResource {
    //注解里面只能声明属性，不能声明方法
    String name() default "";
}
