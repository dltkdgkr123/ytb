package com.sh.ytb;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YoutubeService {

  private final CredentialProperties credentialProperties;
  private final VideoProperties videoProperties;

  String sayHello() {
    System.out.println("hello server");

    return "hello client";
  }

  List<SearchResult> mostPopularVideosGet() {

    JsonFactory jsonFactory = new JacksonFactory();
    NetHttpTransport netHttpTransport = new NetHttpTransport();

    YouTube youtube = new YouTube.Builder(
        netHttpTransport,
        jsonFactory,
        request -> {
        })
        .setApplicationName(videoProperties.getApplicationName())
        .build();

    try {
      YouTube.Search.List search = youtube.search().list("id, snippet");
      search.setKey(credentialProperties.apiKey);
      search.setType("video");
      search.setRegionCode("KR");
      search.setOrder("viewCount");
      search.setMaxResults(videoProperties.getMaxResults());
      search.setQ(videoProperties.getQuery());

      SearchListResponse searchResponse = search.execute();

      return searchResponse.getItems();

    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
