package com.sh.ytb;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.sh.ytb.exception.CredentialNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class OAuthHelper {

  /* FIXME: static 변수들 프로퍼티 객체화 필요 */

  /* Client ID, Secret 포함 JSON 파일 - Google 입장에서 Client인 My Server를 의미 */
  private static final String CLIENT_SECRET_FILE = "client_secret.json";
  private static final String CREDENTIALS_FILE = "StoredCredential";
  private static final String CREDENTIALS_DIRECTORY_PATH = "credentials";
  private static final String TOKEN_SERVER_URL = "https://oauth2.googleapis.com/token";

  /* OS 종류에 따라 경로 구분자가 다를 수 있으므로, 이스케이프 문자 하드 코딩 하지 말라고 함 */
  private static final String CREDENTIALS_FILE_PATH =
      CREDENTIALS_DIRECTORY_PATH + File.separator + CREDENTIALS_FILE;

  /*
   * 글로부터 인가받을 사용자 계정 접근 가능 범위 지정
   * 본 프로젝트에선 사용자의 Youtube Playlist CRUD 동작을 할 수 있어야 하므로, readonly 외에도 force-ssl 권한 필요
   * force-ssl는 민감한 권한 범위로 분류되므로, 프로덕션 모드 전환시 인증 절차가 다소 까다롭다고 함
   * 현재 테스트 모드이므로, 최대 100명의 미리 지정한 사용자만 이용 가능
   */
  private static final List<String> SCOPES = Arrays.asList(
      "https://www.googleapis.com/auth/youtube.readonly",
      "https://www.googleapis.com/auth/youtube.force-ssl");

  /**
   * <p>구글로부터 사용자의 자격 증명(credential)을 인가 받기 위한 {@link GoogleAuthorizationCodeFlow} 객체 생성
   *
   * @return 생성된 {@link GoogleAuthorizationCodeFlow} 객체
   * @author sh
   * @since 1.0
   */
  public GoogleAuthorizationCodeFlow generateAuthorizationFlow()
      throws IOException, GeneralSecurityException {

    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(
            JacksonFactory.getDefaultInstance(),
            new InputStreamReader(Objects.requireNonNull(
                OAuthHelper.class.getClassLoader().getResourceAsStream(CLIENT_SECRET_FILE))));

    return
        new GoogleAuthorizationCodeFlow.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            JacksonFactory.getDefaultInstance(),
            clientSecrets,
            SCOPES
        )
            .setDataStoreFactory(new FileDataStoreFactory(new File(CREDENTIALS_DIRECTORY_PATH)))
            .setAccessType("offline") // 리프레시 토큰을 받을 수 있게 설정
            .build();
  }

  /**
   * <p>{@link GoogleAuthorizationCodeFlow}를 사용하여 사용자가 인증을 시도할
   * {@link GoogleAuthorizationCodeRequestUrl}을 생성하고, 인증 이후 리다이렉트될 URI를
   * {@link GoogleAuthorizationCodeRequestUrl#setRedirectUri(String)}에 등록하여 빌드</p>
   *
   * <p>사용자가 인증을 성공하면 구글이 {@link OAuthHelper#CREDENTIALS_DIRECTORY_PATH}
   * 경로에 StoredCredential 바이너리 파일 생성
   *
   * @return 사용자가 인증을 시도할 URI 문자열
   * @throws GeneralSecurityException 인증 관련 보안 오류 발생 시
   * @throws IOException              입력/출력 관련 오류 발생 시
   * @author sh
   * @since 1.0
   */
  public String getAuthorizationUri() throws GeneralSecurityException, IOException {

    return
        generateAuthorizationFlow()
            .newAuthorizationUrl()
            .setRedirectUri("http://localhost:8080/oauth2callback")
            .build();
  }

  /* NOTE: GoogleCredential은 Deprecate 예정이므로 StoredCredential 사용 */

  /**
   * <p>사용자 인증 성공 이후, 구글에 의해 자동 생성된
   * StoredCredential 바이너리 파일로부터 {@link StoredCredential} 로드
   *
   * @return 중간 객체 역할을 하는 {@link StoredCredential} 객체
   * @throws IOException                 입력/출력 관련 오류 발생 시
   * @throws CredentialNotFoundException 파싱을 시도했을 때 자격 증명이 존재하지 않을 시 (절대 경로 포함하여 로깅)
   * @author sh
   * @since 1.0
   */
  public StoredCredential loadStoredCredential() throws IOException {

    File filePath = new File(CREDENTIALS_FILE_PATH);
    FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(filePath.getParentFile());

    /* FIXME: 단일 유저 아닐 때 get("user") 동작 검증 필요 */
    return
        Optional.ofNullable(
                (StoredCredential) dataStoreFactory
                    .getDataStore(CREDENTIALS_FILE)
                    .get("user"))
            .orElseThrow(
                () -> CredentialNotFoundException.fromAbsoluteFilePath(filePath.getAbsolutePath()));
  }

  /* OPTION: 구글에서 지원하는 dataStore 인터페이스 활용해서 [파일/메모리/스토리지] 시스템 기반 토큰 저장 가능 */

  /**
   * <p>{@link StoredCredential} 객체를 실제 Youtube API 요청에 쓰일 {@link Credential}로 변환
   *
   * @return 구글의 AccessToken, RefreshToken을 포함하는 {@link Credential} 객체
   * @author sh
   * @since 1.0
   */
  public Credential convertToCredential(StoredCredential storedCredential)
      throws GeneralSecurityException, IOException {

    return
        new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
            .setTransport(GoogleNetHttpTransport.newTrustedTransport())
            .setJsonFactory(JacksonFactory.getDefaultInstance())
            /* OPTION: .setTokenServerEncodedUrl() */
            .setTokenServerUrl(new GenericUrl(TOKEN_SERVER_URL))
            /* FIXME: flow 재생성하여 사용 중 - id 및 secret 파싱하여 쓰거나 flow 휘발하지 않고 쓰는 방법 고려 */
            .setClientAuthentication(generateAuthorizationFlow().getClientAuthentication())
            .build()

            .setAccessToken(storedCredential.getAccessToken())
            .setRefreshToken(storedCredential.getRefreshToken())
            .setExpirationTimeMilliseconds(storedCredential.getExpirationTimeMilliseconds());
  }
}