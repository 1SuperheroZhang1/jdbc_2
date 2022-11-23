package com.atguigu4.connection;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class C3P0Test {
	//��ʽһ�����Ƽ�
	@Test
	public void testGetConnection() throws Exception {
		//��ȡc3p0���ݿ����ӳ�
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "com.mysql.cj.jdbc.Driver" );            
		cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/tmp" );
		cpds.setUser("root");                                  
		cpds.setPassword("020704");
		
		//ͨ��������صĲ����������ݿ����ӳؽ��й���
		//���ó�ʼʱ���ݿ����ӳ��е�������
		cpds.setInitialPoolSize(10);
		
		Connection conn = cpds.getConnection();
		System.out.println(conn);
		
		//����c3p0���ݿ����ӳ�
		DataSources.destroy(cpds);
	}
	//��ʽ����ʹ�������ļ�
	@Test
	public void testGetConnection1() throws SQLException {
		ComboPooledDataSource cpds = new ComboPooledDataSource("helloC3P0");  
		Connection conn = cpds.getConnection();
		System.out.println(conn);
	}
}
