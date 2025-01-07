package com.sh.ytb;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.sh.ytb.exception.CredentialNotFoundException;
import com.sh.ytb.exception.GoogleAuthFlowNotGenerateException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OAuthHelper {

  private static final String CLIENT_SECRET_FILE = "client_secret.json"; // Client ID, Secret 포함 JSON 파일
  private static final String CREDENTIALS_FILE = "StoredCredential";
  private static final String CREDENTIALS_DIRECTORY_PATH = "credentials";
  // OS따라 경로 구분자(이스케이프 문자)가 다를 수 있으므로, 하드 코딩 하지 말라고 함
  private static final String CREDENTIALS_FILE_PATH =
      CREDENTIALS_DIRECTORY_PATH + File.separator + CREDENTIALS_FILE;

  private static final List<String> SCOPES = Arrays.asList(
      "https://www.googleapis.com/auth/youtube.readonly",
      "https://www.googleapis.com/auth/youtube.force-ssl");

  // AuthorizationFlow 생성
  public Optional<GoogleAuthorizationCodeFlow> getAuthorizationFlow() {

    try {
      GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
          JacksonFactory.getDefaultInstance(),
          new InputStreamReader(
              Objects.requireNonNull(
                  OAuthHelper.class.getClassLoader().getResourceAsStream(CLIENT_SECRET_FILE)))
      );

      return Optional.ofNullable(
          new GoogleAuthorizationCodeFlow.Builder(
              GoogleNetHttpTransport.newTrustedTransport(),
              JacksonFactory.getDefaultInstance(),
              clientSecrets,
              SCOPES
          )
              .setDataStoreFactory(
                  new FileDataStoreFactory(new File(CREDENTIALS_DIRECTORY_PATH)))
              .setAccessType("offline") // 리프레시 토큰을 받을 수 있게 설정
              .build());

    } catch (IOException | GeneralSecurityException e) {
      log.error("Unexpected error: {}", e.getMessage(), e);
      return Optional.empty();
    }
  }


  // 사용자가 인증을 위해 클릭할 수 있는 URI 생성
  public String authorizationUriGet() {

    return getAuthorizationFlow()
        .orElseThrow(GoogleAuthFlowNotGenerateException::new)
        .newAuthorizationUrl()
        .setRedirectUri("http://localhost:8080/oauth2callback")
        .build();
  }

  // StoredCredential 사용 - GoogleCredential 비권장
  public StoredCredential loadCredentialObjFromStoredCredential() throws IOException {

    File filePath = new File(CREDENTIALS_FILE_PATH);
    FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(filePath.getParentFile());

    // 단일 유저 아닐 때 get("user") 동작 검증 필요
    return Optional.ofNullable((StoredCredential) dataStoreFactory
            .getDataStore(CREDENTIALS_FILE)
            .get("user"))
        .orElseThrow(
            () -> CredentialNotFoundException.fromAbsoluteFilePath(filePath.getAbsolutePath())
        );
  }
}
