package com.sh.ytb.properties;

import org.springframework.stereotype.Component;

/* FIXME : 프젝 볼륨 커지면 삭제 */
@Component
@SuppressWarnings("squid:S115")
public class VideoProperties {

  private VideoProperties() {
  }

  public static final String video_application_name = "TopVideos";
  public static final Long video_max_results = 10L;
  public static final String video_query = "음악";
  public static final String video_region_code = "KR";
  public static final String video_order = "viewCount";
  public static final String video_type = "video";
  public static final String video_scope = "id, snippet";
}
