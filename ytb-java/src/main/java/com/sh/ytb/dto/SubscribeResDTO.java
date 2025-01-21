package com.sh.ytb.dto;


import com.google.api.client.util.DateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SubscribeResDTO {

  // DTO 자체 고유 식별자
  private Long id;

  private String title;

  private String channelId;

  /* OPTION: 해상도 default, medium, high 존재 (현재 medium 채택) */
  private String thumbnailUrl;

  /* OPTION: etag -> 리소스의 최신 상태 추적용 (활용해서 성능 개선 가능) */
  private String etag;

  private DateTime publishedAt;
}
