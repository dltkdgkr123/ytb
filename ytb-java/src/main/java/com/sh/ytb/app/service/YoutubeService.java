package com.sh.ytb.app.service;

import com.sh.ytb.adapter.out.google.YoutubeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YoutubeService {

  private final YoutubeHelper youtubeHelper;
}
