package com.sh.ytb;

import com.google.api.services.youtube.model.SearchResult;
import java.util.List;
import java.util.NoSuchElementException;
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

  @GetMapping
  ResponseEntity<String> hello() {
    String msg = youtubeService.sayHello();

    return ResponseEntity.status(HttpStatus.OK).body(msg);
  }

  @GetMapping("/video/mostPopular")
  ResponseEntity<List<SearchResult>> getMostPopularVideos() {

    List<SearchResult> results = youtubeService.mostPopularVideosGet()
        .orElseThrow(NoSuchElementException::new);

    return ResponseEntity.status(HttpStatus.OK).body(results);
  }

  @GetMapping("/auth")
  ResponseEntity<String> getAuthorizationUri() {

    String uri = youtubeService.authorizationUriGet()
        .orElseThrow(NoSuchElementException::new);

    return ResponseEntity.status(HttpStatus.OK).body(uri);
  }
}