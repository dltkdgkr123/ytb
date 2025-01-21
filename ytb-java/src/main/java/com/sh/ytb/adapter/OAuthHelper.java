package com.sh.ytb.adapter;

import static com.sh.ytb.properties.OAuthProperties.oauth_secret_file;
import static com.sh.ytb.properties.OAuthProperties.oauth_directory_path;
import static com.sh.ytb.properties.OAuthProperties.oauth_credential_file;
import static com.sh.ytb.properties.OAuthProperties.oauth_credential_file_path;
import static com.sh.ytb.properties.OAuthProperties.oauth_request_scopes;
import static com.sh.ytb.properties.OAuthProperties.oauth_token_server_url;

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
import com.sh.ytb.annotation.Adapter;
import com.sh.ytb.exception.CredentialNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.Optional;

@Adapter
public class OAuthHelper {

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
                OAuthHelper.class.getClassLoader().getResourceAsStream(oauth_secret_file))));

    return
        new GoogleAuthorizationCodeFlow.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            JacksonFactory.getDefaultInstance(),
            clientSecrets,
            oauth_request_scopes
        )
            .setDataStoreFactory(new FileDataStoreFactory(new File(oauth_directory_path)))
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

    File filePath = new File(oauth_credential_file_path);

    /* FIXME: 단일 유저 아닐 때 get("user") 동작 검증 필요 */
    return
        Optional.ofNullable((StoredCredential) new FileDataStoreFactory(
                new File(oauth_credential_file_path).getParentFile())
                .getDataStore(oauth_credential_file)
                .get("user"))
            .orElseThrow(
                () -> CredentialNotFoundException.fromAbsoluteFilePath(filePath.getAbsolutePath()));
  }

  /* OPTION: 구글에서 지원하는 dataStore 인터페이스 활용해서 [파일/메모리/스토리지] 시스템 기반 토큰 저장 가능 */
  public String getAccessToken(StoredCredential storedCredential) {

    return storedCredential.getAccessToken();
  }

  public String getRefreshToken(StoredCredential storedCredential) {

    return storedCredential.getRefreshToken();
  }

  public Credential loadCredential(
      String accessToken,
      String refreshToken,
      Long expirationTimeMillis)
      throws GeneralSecurityException, IOException {

    return
        new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
            .setTransport(GoogleNetHttpTransport.newTrustedTransport())
            .setJsonFactory(JacksonFactory.getDefaultInstance())
            /* OPTION: .setTokenServerEncodedUrl() */
            .setTokenServerUrl(new GenericUrl(oauth_token_server_url))
            .setClientAuthentication(generateAuthorizationFlow().getClientAuthentication())
            .build()

            .setAccessToken(accessToken)
            .setRefreshToken(refreshToken)
            .setExpirationTimeMilliseconds(expirationTimeMillis);
  }
}