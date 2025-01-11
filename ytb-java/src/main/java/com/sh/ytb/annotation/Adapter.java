package com.sh.ytb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/* OPTION: 클린 아키텍처를 쫓다가 배보다 배꼽이 더 커지는 사항
 *
 * 1.Controller 또한 브라우저와 내 앱을 이어주는 Adapter (헥사고날 아키텍처에서 배운 내용)
 * 2. Adapter 끼울 port 레이어 구성 (현재 Service 바로 연결 중)
 */

/**
 * <p> 외부 시스템(Colud, Storage, Open API, Frameworks..)과 WAS 를 연결하는 레이어에 적용
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