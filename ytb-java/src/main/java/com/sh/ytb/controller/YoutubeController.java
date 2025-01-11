package com.sh.ytb.controller;

import com.google.api.services.youtube.model.SearchResult;
import com.sh.ytb.service.YoutubeService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/youtube")
@RequiredArgsConstructor
class YoutubeController {

  private final YoutubeService youtubeService;

  /* FIXME : 프젝 볼륨 커지면 삭제 */
  @GetMapping("/video/mostPopular")
  ResponseEntity<List<SearchResult>> getMostPopularVideos() throws IOException {

    List<SearchResult> results = youtubeService.mostPopularVideosGet();

    return ResponseEntity.status(HttpStatus.OK).body(results);
  }

  @GetMapping("/auth")
  ResponseEntity<String> getAuthorizationUri() throws GeneralSecurityException, IOException {

    String uri = youtubeService.authorizationUriGet();

    return ResponseEntity.status(HttpStatus.OK).body(uri);
  }
}