package com.atguigu4.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

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
	 * @Description ʹ��C3P0�����ݿ����ӳؼ���
	 * @author ����ɭ
	 * @return
	 * @throws SQLException
	 * */
	//���ݿ����ӳ�ֻ���ṩһ�����ɡ�
	private static ComboPooledDataSource cpds = new ComboPooledDataSource("helloC3P0");  
	public static Connection getConnection1() throws Exception {
		Connection conn = cpds.getConnection();
		
		return conn;
	}
	
	/**
	 * @Description ʹ��DBCP���ݿ����ӳؼ�����ȡ���ݿ�����
	 * @author ����ɭ
	 * @return
	 * @throws Exception
	 * */
	private static DataSource source;
	static {
		Properties pros = new Properties();
		
		//��ʽ1��
//		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
		//��ʽ2��
		try {
			FileInputStream is=new FileInputStream(new File("src/dbcp.properties"));
			pros.load(is);
			DataSource source = BasicDataSourceFactory.createDataSource(pros);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConnection2() throws Exception {
		
		Connection conn = source.getConnection();

		return conn;
	}
	
	/**
	 * @Description ʹ��Druid���ݿ����ӳؼ�����ȡ���ݿ�����
	 * @author ����ɭ
	 * @return
	 * @throws Exception
	 * */
	private static DataSource source1;
	static {
		try {
			Properties pros = new Properties();
			
//		��ʽ1��
//		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
			//��ʽ2��
			FileInputStream is=new FileInputStream(new File("src/druid.properties"));
			pros.load(is);
			source1 = DruidDataSourceFactory.createDataSource(pros);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static Connection getConnection3() throws Exception {
		
		Connection conn = source1.getConnection();
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
	/**
	 * @Description ʹ��DbUtils.jar���ṩ��DbUtils�����࣬ʵ����Դ�Ĺر�
	 * @author ����ɭ
	 * @param conn
	 * @param ps
	 * @param rs
	 * */
	public static void closeResource1(Connection conn,Statement ps,ResultSet rs) {
//		try {
//			DbUtils.close(conn);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			DbUtils.close(ps);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			DbUtils.close(rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		DbUtils.closeQuietly(conn);
		DbUtils.closeQuietly(ps);
		DbUtils.closeQuietly(rs);
	}
}
