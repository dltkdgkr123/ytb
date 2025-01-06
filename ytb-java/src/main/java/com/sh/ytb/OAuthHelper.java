package com.sh.ytb;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class OAuthHelper {

  private static final String CLIENT_SECRET_FILE = "client_secret.json"; // Client ID, Secret 포함 JSON 파일
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Arrays.asList(
      "https://www.googleapis.com/auth/youtube.readonly",
      "https://www.googleapis.com/auth/youtube.force-ssl");

  // AuthorizationFlow 생성
  public GoogleAuthorizationCodeFlow getAuthorizationFlow() throws Exception {
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
        JacksonFactory.getDefaultInstance(),
        new InputStreamReader(
            Objects.requireNonNull(
                OAuthHelper.class.getClassLoader().getResourceAsStream(CLIENT_SECRET_FILE)))
    );

    return new GoogleAuthorizationCodeFlow.Builder(
        GoogleNetHttpTransport.newTrustedTransport(),
        JacksonFactory.getDefaultInstance(),
        clientSecrets,
        SCOPES
    )
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline") // 리프레시 토큰을 받을 수 있게 설정
        .build();
  }

  // 사용자가 인증을 위해 클릭할 수 있는 URI 생성
  public String authorizationUriGet() throws Exception {
    GoogleAuthorizationCodeFlow flow = getAuthorizationFlow();
    return flow.newAuthorizationUrl().setRedirectUri("http://localhost:8080/oauth2callback")
        .build();
  }

  // 사용자 인증 이후, 헤딩 코드로 Credential 생성
  public Credential getCredentialFromCode(String code) throws Exception {
    GoogleAuthorizationCodeFlow flow = getAuthorizationFlow();

    // 인증 코드와 리디렉션 URI를 이용해 토큰 요청
    TokenResponse tokenResponse = flow.newTokenRequest(code)
        .setRedirectUri("http://localhost:8080/oauth2callback")
        .execute();

    return flow.createAndStoreCredential(tokenResponse, "user");
  }
}
