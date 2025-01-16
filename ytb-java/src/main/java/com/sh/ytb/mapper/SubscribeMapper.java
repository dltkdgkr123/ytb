package com.sh.ytb.mapper;

import com.google.api.services.youtube.model.Subscription;
import com.google.api.services.youtube.model.SubscriptionSnippet;
import com.sh.ytb.dto.SubscribeDTO;
import com.sh.ytb.entity.SubScribeJPAEntity;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SubscribeMapper {

  /* Obj -> DTO */
  public SubscribeDTO mapObjToDTO(Subscription subscription) {

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
  public List<SubscribeDTO> mapObjToDTO(List<Subscription> subscriptions) {

    return subscriptions.stream()
        .map(this::mapObjToDTO)
        .toList();
  }

  /* Obj Array -> DTO List */
  public List<SubscribeDTO> mapObjToDTO(Subscription... subscriptions) {

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
  public SubScribeJPAEntity mapDTOToJPAEntity(SubscribeDTO subscribeDTO) {

    return
        SubScribeJPAEntity.builder()
            .title(subscribeDTO.getTitle())
            .channelId(subscribeDTO.getChannelId())
            .thumbnailUrl(subscribeDTO.getThumbnailUrl())
            .etag(subscribeDTO.getEtag())
            .publishedAt(subscribeDTO.getPublishedAt())
            .build();
  }


  /* DTO List -> Entity List */
  public List<SubScribeJPAEntity> mapDTOToJPAEntity(List<SubscribeDTO> subscribeDTOs) {

    return subscribeDTOs.stream()
        .map(this::mapDTOToJPAEntity)
        .toList();
  }

  /* DTO Array -> Entity List */
  public List<SubScribeJPAEntity> mapDTOOToJPAEntity(SubscribeDTO... subscribeDTOs) {

    return mapDTOToJPAEntity(Arrays.asList(subscribeDTOs));
  }
}
