package com.sh.ytb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;


/* OPTION: 엄밀히 분류하면 Controller 또한 브라우저와 내 앱을 이어주는 Adapter 이다. (헥사고날 아키텍처에서 배운 내용)
 * 다만, 현재 디자인 패턴에서 이렇게 처리하면 배보다 배꼽이 더 커질것 같아서 일단 미포함
 */
/**
 * <p> 외부 시스템(Colud, Storage, Open API, Frameworks..)과 현재 웹서버를 연결하는 레이어에 적용
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