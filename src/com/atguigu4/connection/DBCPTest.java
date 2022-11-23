package com.atguigu4.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

public class DBCPTest {
	
	//��ʽһ�����Ƽ�
	/**
	 * @Description  ����DBCP�����ݿ����ӳؼ���
	 * @author ����ɭ
	 * @throws SQLException 
	 * 
	 * */
	@Test
	public void testGetConnection() throws SQLException {
		//����DBCP�����ݿ����ӳ�
		BasicDataSource source=new BasicDataSource();
		
		//���û�����Ϣ
		source.setDriverClassName("com.mysql.cj.jdbc.Driver");
		source.setUrl("jdbc:mysql:///tmp");
		source.setUsername("root");
		source.setPassword("020704");
		
		//��������������������ݿ����ӳع����������ԣ�
		source.setInitialSize(10);
		source.setMaxActive(10);
		//...
		
		Connection conn = source.getConnection();
		System.out.println(conn);
	}
	
	//��ʽ�����Ƽ���ʹ�������ļ�
	@Test
	public void testGetConnection1() throws Exception {
		
		Properties pros = new Properties();
		
		//��ʽ1��
//		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
		//��ʽ2��
		FileInputStream is=new FileInputStream(new File("src/dbcp.properties"));
		pros.load(is);
		DataSource source = BasicDataSourceFactory.createDataSource(pros);
		Connection conn = source.getConnection();
		System.out.println(conn);
	}
}
