package com.sh.ytb.service;

import com.google.api.services.youtube.model.SearchResult;
import com.sh.ytb.adapter.YoutubeHelper;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YoutubeService {

  private final YoutubeHelper youtubeHelper;

  public List<SearchResult> mostPopularVideosGet() throws IOException, GeneralSecurityException {

    return youtubeHelper.mostPopularVideosGet();
  }
}
