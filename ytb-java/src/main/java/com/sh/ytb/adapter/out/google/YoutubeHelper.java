package com.sh.ytb.adapter.out.google;

import static com.sh.ytb.common.properties.SubscribeProperties.subscribe_application_name;
import static com.sh.ytb.common.properties.SubscribeProperties.subscribe_is_mine;
import static com.sh.ytb.common.properties.SubscribeProperties.subscribe_max_results;
import static com.sh.ytb.common.properties.SubscribeProperties.subscribe_scope;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Subscription;
import com.sh.ytb.common.annotation.Adapter;
import com.sh.ytb.common.properties.secret.CredentialProperties;
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
        GsonFactory.getDefaultInstance(),
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
}
