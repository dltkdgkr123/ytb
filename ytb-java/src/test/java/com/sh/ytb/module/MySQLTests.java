package com.sh.ytb.module;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MySQLTests {

  @Autowired
  private DataSource dataSource;

  @Test
  public void testConnection() throws SQLException {

    assertDoesNotThrow(() -> Exception.class);
    assertNotNull(dataSource.getConnection());
  }
}

