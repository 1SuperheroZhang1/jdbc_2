package com.atguigu4.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

public class DruidTest {

	//��ʽһ�����Ƽ�
		/**
		 * @Description  ����Druid�����ݿ����ӳؼ���
		 * @author ����ɭ
		 * @throws SQLException 
		 * 
		 * */
	@Test
	public void testGetConnection() throws SQLException {
		//����Druid�����ݿ����ӳ�
		DruidDataSource source=new DruidDataSource();
		
		//���û�����Ϣ
		source.setDriverClassName("com.mysql.cj.jdbc.Driver");
		source.setUrl("jdbc:mysql:///tmp");
		source.setUsername("root");
		source.setPassword("020704");
		
		//��������������������ݿ����ӳع����������ԣ�
		source.setInitialSize(10);
		source.setMaxActive(10);
		//...
		
		DruidPooledConnection conn = source.getConnection();
		
		System.out.println(conn);
	}
	
	//��ʽ�����Ƽ���ʹ�������ļ�
	@Test
	public void testGetConnection1() throws Exception {
		Properties pros = new Properties();
		
//		��ʽ1��
//		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
		//��ʽ2��
		FileInputStream is=new FileInputStream(new File("src/druid.properties"));
		pros.load(is);
		DataSource source = DruidDataSourceFactory.createDataSource(pros);
		
		Connection conn = source.getConnection();
		System.out.println(conn);
	}
	
}
