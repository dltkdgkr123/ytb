package com.sh.ytb.module;

import java.sql.Connection;
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
    try (Connection connection = dataSource.getConnection()) {
      System.out.println("MySQL 연결 성공: " + connection.isValid(2));
    } catch (Exception e) {
      System.out.println("MySQL 연결 실패: " + e.getMessage());
      throw e;
    }
  }
}

