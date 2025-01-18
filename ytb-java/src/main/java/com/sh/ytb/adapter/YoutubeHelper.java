package com.sh.ytb.adapter;

import static com.sh.ytb.properties.SubscribeProperties.subscribe_application_name;
import static com.sh.ytb.properties.SubscribeProperties.subscribe_is_mine;
import static com.sh.ytb.properties.SubscribeProperties.subscribe_max_results;
import static com.sh.ytb.properties.SubscribeProperties.subscribe_scope;
import static com.sh.ytb.properties.VideoProperties.video_application_name;
import static com.sh.ytb.properties.VideoProperties.video_max_results;
import static com.sh.ytb.properties.VideoProperties.video_order;
import static com.sh.ytb.properties.VideoProperties.video_query;
import static com.sh.ytb.properties.VideoProperties.video_region_code;
import static com.sh.ytb.properties.VideoProperties.video_scope;
import static com.sh.ytb.properties.VideoProperties.video_type;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Subscription;
import com.sh.ytb.annotation.Adapter;
import com.sh.ytb.properties.secret.CredentialProperties;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class YoutubeHelper {

  private final CredentialProperties credentialProperties;

  public List<Subscription> getSubscribedChannels(Credential credential)
      throws GeneralSecurityException, IOException {

    YouTube youtube = new YouTube.Builder(
        GoogleNetHttpTransport.newTrustedTransport(),
        JacksonFactory.getDefaultInstance(),
        credential)
        .setApplicationName(subscribe_application_name)
        .build();

    return
        youtube.subscriptions()
            .list(subscribe_scope)
            .setMine(subscribe_is_mine)
            .setMaxResults(subscribe_max_results)
            .execute().getItems();
  }

  /* FIXME : 프젝 볼륨 커지면 삭제 */
  public List<SearchResult> mostPopularVideosGet() throws IOException, GeneralSecurityException {

    YouTube youtube = new YouTube.Builder(
        GoogleNetHttpTransport.newTrustedTransport(),
        JacksonFactory.getDefaultInstance(),
        request -> {
        })
        .setApplicationName(video_application_name)
        .build();

    return
        youtube.search()
            .list(video_scope)
            .setKey(credentialProperties.getApiKey())
            .setType(video_type)
            .setRegionCode(video_region_code)
            .setOrder(video_order)
            .setMaxResults(video_max_results)
            .setQ(video_query)
            .execute().getItems();
  }
}
