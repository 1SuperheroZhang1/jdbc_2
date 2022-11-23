package com.atguigu1.transaction;

import java.sql.Connection;

import org.junit.Test;

import com.atguigu1.utils.JDBCUtils;

public class ConnectionTest {
	@Test
	public void testConnection() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		System.out.println(conn);
	}
}
