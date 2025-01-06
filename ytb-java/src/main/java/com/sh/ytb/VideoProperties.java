package com.sh.ytb;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class VideoProperties {

  private final String applicationName = "YouTubeTopVideos";
  private final Long maxResults = 10L;
  private final String query = "음악";
}
