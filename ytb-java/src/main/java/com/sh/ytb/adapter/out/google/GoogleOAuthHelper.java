package com.sh.ytb.adapter.out.google;

import static com.sh.ytb.common.properties.OAuthProperties.oauth_access_type;
import static com.sh.ytb.common.properties.OAuthProperties.oauth_callback_uri;
import static com.sh.ytb.common.properties.OAuthProperties.oauth_redirect_uri;
import static com.sh.ytb.common.properties.OAuthProperties.oauth_request_scopes;
import static com.sh.ytb.common.properties.OAuthProperties.oauth_secret_file;
import static com.sh.ytb.common.properties.OAuthProperties.oauth_token_expiration_time_millis;
import static com.sh.ytb.common.properties.OAuthProperties.oauth_token_server_url;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.gson.GsonFactory;
import com.sh.ytb.adapter.out.spec.OAuthAdapter;
import com.sh.ytb.common.annotation.Adapter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Adapter
@RequiredArgsConstructor
public class GoogleOAuthHelper implements OAuthAdapter {

  /* FIXME: 현재 null인 상태 -> 세션에서 가져오는 형식 구현, 테스트시 모킹 */
  private final Long id;

  /**
   *
   * <p>구글로부터 사용자의 자격 증명(credential)을 인가 받기 위한 {@link GoogleAuthorizationCodeFlow} 객체 생성
   *
   * @return 생성된 {@link GoogleAuthorizationCodeFlow} 객체
   * @author sh
   * @since 1.0
   */
  private GoogleAuthorizationCodeFlow generateAuthorizationFlow()
      throws IOException, GeneralSecurityException {

    /* FIXME: 파일 시스템 기반 제거, 서버 환경변수 및 프로퍼티 객체로 할당 */
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(
            GsonFactory.getDefaultInstance(),
            new InputStreamReader(Objects.requireNonNull(
                GoogleOAuthHelper.class.getClassLoader().getResourceAsStream(oauth_secret_file))));

    return
        new GoogleAuthorizationCodeFlow.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory.getDefaultInstance(),
            clientSecrets,
            oauth_request_scopes
        )
//            .setDataStoreFactory(new FileDataStoreFactory(new File(oauth_directory_path)))
            .setAccessType(oauth_access_type)
            .build();
  }

  /**
   * <p>{@link GoogleAuthorizationCodeFlow}를 사용하여 사용자가 인증을 시도할
   * {@link GoogleAuthorizationCodeRequestUrl}을 생성하고, 인증 이후 리다이렉트될 URI를
   * {@link GoogleAuthorizationCodeRequestUrl#setRedirectUri(String)}에 등록하여 빌드</p>
   *
   * <p>사용자가 인증을 성공하면 구글이  StoredCredential 바이너리 파일 생성
   *
   * @return 사용자가 인증을 시도할 URI 문자열
   * @throws GeneralSecurityException 인증 관련 보안 오류 발생 시
   * @throws IOException              입력/출력 관련 오류 발생 시
   * @author sh
   * @since 1.0
   */
  public String getAuthorizationUri() throws GeneralSecurityException, IOException {

    return
        this.generateAuthorizationFlow()
            .newAuthorizationUrl()
            .setRedirectUri(oauth_callback_uri)
            .build();
  }

  @Transactional
  public Credential afterCallback(String code) throws GeneralSecurityException, IOException {

    /* GoogleTokenResponse implements TokenResponse */
    return this.createAndStoreCredential(this.sendTokenRequestToGoogle(code), id);
  }

  private GoogleTokenResponse sendTokenRequestToGoogle(String code)
      throws GeneralSecurityException, IOException {

    return
        this.generateAuthorizationFlow()
            .newTokenRequest(code)
            .setRedirectUri(oauth_redirect_uri)
            .execute();
  }

  private Credential createAndStoreCredential(TokenResponse response, Long id)
      throws IOException, GeneralSecurityException {

    return
        new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
            .setTransport(GoogleNetHttpTransport.newTrustedTransport())
            .setJsonFactory(GsonFactory.getDefaultInstance())
            /* OPTION: .setTokenServerEncodedUrl() */
            .setTokenServerUrl(new GenericUrl(oauth_token_server_url))
            .setClientAuthentication(generateAuthorizationFlow().getClientAuthentication())
            .build()

            .setAccessToken(response.getAccessToken())
            .setRefreshToken(response.getRefreshToken())
            .setExpirationTimeMilliseconds(oauth_token_expiration_time_millis);
  }
}