package com.sh.ytb.properties;

import java.io.File;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings({"squid:S115", "java:S2386"})
public class OAuthProperties {

  private OAuthProperties() {
  }

  /* Client ID, Secret 포함 JSON 파일 - Google 입장에서 Client인 My Server를 의미 */
  public static final String oauth_secret_file = "client_secret.json";

  public static final String oauth_credential_file = "StoredCredential";

  public static final String oauth_directory_path = "credentials";

  public static final String oauth_token_server_url = "https://oauth2.googleapis.com/token";

  /* OS 종류에 따라 경로 구분자가 다를 수 있으므로, 이스케이프 문자 하드 코딩 하지 말라고 함 */
  public static final String oauth_credential_file_path =
      oauth_directory_path + File.separator + oauth_credential_file;

  /*
   * 글로부터 인가받을 사용자 계정 접근 가능 범위 지정
   * 본 프로젝트에선 사용자의 Youtube Playlist CRUD 동작을 할 수 있어야 하므로, readonly 외에도 force-ssl 권한 필요
   * force-ssl는 민감한 권한 범위로 분류되므로, 프로덕션 모드 전환시 인증 절차가 다소 까다롭다고 함
   * 현재 테스트 모드이므로, 최대 100명의 미리 지정한 사용자만 이용 가능
   */
  public static final List<String> oauth_request_scopes = List.of(
      "https://www.googleapis.com/auth/youtube.readonly",
      "https://www.googleapis.com/auth/youtube.force-ssl"
  ); // It's ImmutableList
}
