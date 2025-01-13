package com.sh.ytb.mapper;

import com.google.api.services.youtube.model.Subscription;
import com.google.api.services.youtube.model.SubscriptionSnippet;
import com.sh.ytb.dto.SubscribeDTO;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SubscribeMapper {

  /* Obj -> DTO */
  public SubscribeDTO mapToDTO(Subscription subscription) {

    SubscriptionSnippet snippet = subscription.getSnippet();

    return
        SubscribeDTO.builder()
            .title(snippet.getTitle())
            .channelId(snippet.getChannelId())
            .thumbnailUrl(snippet.getThumbnails().getMedium().getUrl())
            .etag(subscription.getEtag())
            .publishedAt(snippet.getPublishedAt())
            .build();
  }

  /* Obj List -> DTO List */
  public List<SubscribeDTO> mapToDTO(List<Subscription> subscriptions) {

    return subscriptions.stream()
        .map(this::mapToDTO)
        .toList();
  }

  /* Obj Array -> DTO List */
  public List<SubscribeDTO> mapToDTO(Subscription... subscriptions) {

    return mapToDTO(Arrays.asList(subscriptions));
  }


  /* DTO -> Entity */
  void mapToJPAEntity() {

  }
}
