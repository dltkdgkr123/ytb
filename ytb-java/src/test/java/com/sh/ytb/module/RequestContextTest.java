package com.sh.ytb.module;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.security.enabled=false",
    "logging.level.org.springframework.web=DEBUG"})
public class RequestContextTest {

  @Autowired
  private MockMvc mockMvc;

  /* FIXME: 테스트실패 - 200 예상, 401 반환 */
  @Test
  void testRequestContext() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/test-endpoint"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

    System.out.println("RequestAttributes: " + requestAttributes);
    assertNotNull(requestAttributes);
  }

  @RestController
  public static class TestController {

    @GetMapping("/test-endpoint")
    public String test() {

      return "RequestContext is valid";
    }
  }
}
