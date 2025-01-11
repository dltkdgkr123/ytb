package com.sh.ytb.properties;

import lombok.Getter;
import org.springframework.stereotype.Component;

/* FIXME : 프젝 볼륨 커지면 삭제 */
@Component
@Getter
public class VideoProperties {

  private final String applicationName = "YouTubeTopVideos";
  private final Long maxResults = 10L;
  private final String query = "음악";
}
