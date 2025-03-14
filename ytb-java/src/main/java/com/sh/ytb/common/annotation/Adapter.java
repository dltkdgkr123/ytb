package com.sh.ytb.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * <p> 외부 시스템(Browser, Cloud, Storage..)과 WAS를 연결하는 레이어에 적용
 * 클래스 구조 상 분류를 위함이며, port 및 외부 시스템과의 adapt 동작은 MVP 제작 이후 필요하면 구현
 *
 * @author sh
 * @since 1.0
 */
@Target({ElementType.TYPE}) // scope : class, interface, enum
@Retention(RetentionPolicy.RUNTIME) //  default : RetentionPolicy.CLASS
@Documented
@Component
public @interface Adapter {

  @AliasFor(annotation = Component.class)
  String value() default "";
}