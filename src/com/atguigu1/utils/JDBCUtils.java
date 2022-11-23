package com.atguigu1.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Description ��ȡ���ݿ������
 * @author ����ɭ
 * @date 
 * */
public class JDBCUtils {
	public static Connection getConnection() throws Exception {
		//1.��ȡ�����ļ��е�4��������Ϣ
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
		
		Properties pros=new Properties();
		pros.load(is);
		
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		String driverClass = pros.getProperty("driverClass");
		
		//2.��������
		Class.forName(driverClass);
		//3.��ȡ����
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	/**
	 * @Description �ر����Ӻ�Statement�Ĳ���
	 * @author ����ɭ
	 * */
	public static void closeResource(Connection conn,Statement ps) {
		//7.��Դ�Ĺر�
		try {
			if(ps!=null) {
				ps.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(conn!=null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @Description ����Դ�Ĺر�
	 * @author ����ɭ
	 * */
	public static void closeResource(Connection conn,Statement ps,ResultSet rs) {
		//7.��Դ�Ĺر�
		try {
			if(ps!=null) {
				ps.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(conn!=null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}try {
			if(rs!=null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	}
