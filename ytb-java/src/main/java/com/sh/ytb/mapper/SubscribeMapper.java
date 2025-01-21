package com.sh.ytb.mapper;

import com.google.api.services.youtube.model.Subscription;
import com.google.api.services.youtube.model.SubscriptionSnippet;
import com.sh.ytb.dto.res.SubscribeResDTO;
import com.sh.ytb.entity.SubScribeJPAEntity;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SubscribeMapper {

  /* Obj -> DTO */
  public SubscribeResDTO mapObjToDTO(Subscription subscription) {

    SubscriptionSnippet snippet = subscription.getSnippet();

    return
        SubscribeResDTO.builder()
            .title(snippet.getTitle())
            .channelId(snippet.getChannelId())
            .thumbnailUrl(snippet.getThumbnails().getMedium().getUrl())
            .etag(subscription.getEtag())
            .publishedAt(snippet.getPublishedAt())
            .build();
  }

  /* Obj List -> DTO List */
  public List<SubscribeResDTO> mapObjToDTO(List<Subscription> subscriptions) {

    return subscriptions.stream()
        .map(this::mapObjToDTO)
        .toList();
  }

  /* Obj Array -> DTO List */
  public List<SubscribeResDTO> mapObjToDTO(Subscription... subscriptions) {

    return mapObjToDTO(Arrays.asList(subscriptions));
  }

  /* Obj -> Entity */
  public SubScribeJPAEntity mapObjToJPAEntity(Subscription subscription) {

    SubscriptionSnippet snippet = subscription.getSnippet();

    return
        SubScribeJPAEntity.builder()
            .title(snippet.getTitle())
            .channelId(snippet.getChannelId())
            .thumbnailUrl(snippet.getThumbnails().getMedium().getUrl())
            .etag(subscription.getEtag())
            .publishedAt(snippet.getPublishedAt())
            .build();
  }

  /* Obj List -> Entity List */
  public List<SubScribeJPAEntity> mapObjToJPAEntity(List<Subscription> subscriptions) {

    return subscriptions.stream()
        .map(this::mapObjToJPAEntity)
        .toList();
  }

  /* Obj Array -> Entity List */
  public List<SubScribeJPAEntity> mapObjToJPAEntity(Subscription... subscriptions) {

    return mapObjToJPAEntity(Arrays.asList(subscriptions));
  }

  /* DTO -> Entity */
  public SubScribeJPAEntity mapDTOToJPAEntity(SubscribeResDTO subscribeResDTO) {

    return
        SubScribeJPAEntity.builder()
            .title(subscribeResDTO.getTitle())
            .channelId(subscribeResDTO.getChannelId())
            .thumbnailUrl(subscribeResDTO.getThumbnailUrl())
            .etag(subscribeResDTO.getEtag())
            .publishedAt(subscribeResDTO.getPublishedAt())
            .build();
  }


  /* DTO List -> Entity List */
  public List<SubScribeJPAEntity> mapDTOToJPAEntity(List<SubscribeResDTO> subscribeResDTOS) {

    return subscribeResDTOS.stream()
        .map(this::mapDTOToJPAEntity)
        .toList();
  }

  /* DTO Array -> Entity List */
  public List<SubScribeJPAEntity> mapDTOOToJPAEntity(SubscribeResDTO... subscribeResDTOS) {

    return mapDTOToJPAEntity(Arrays.asList(subscribeResDTOS));
  }
}
