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

	//方式一：不推荐
		/**
		 * @Description  测试Druid的数据库连接池技术
		 * @author 张先森
		 * @throws SQLException 
		 * 
		 * */
	@Test
	public void testGetConnection() throws SQLException {
		//创建Druid的数据库连接池
		DruidDataSource source=new DruidDataSource();
		
		//设置基本信息
		source.setDriverClassName("com.mysql.cj.jdbc.Driver");
		source.setUrl("jdbc:mysql:///tmp");
		source.setUsername("root");
		source.setPassword("020704");
		
		//还可以设置其他设计数据库连接池管理的相关属性；
		source.setInitialSize(10);
		source.setMaxActive(10);
		//...
		
		DruidPooledConnection conn = source.getConnection();
		
		System.out.println(conn);
	}
	
	//方式二：推荐：使用配置文件
	@Test
	public void testGetConnection1() throws Exception {
		Properties pros = new Properties();
		
//		方式1：
//		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
		//方式2：
		FileInputStream is=new FileInputStream(new File("src/druid.properties"));
		pros.load(is);
		DataSource source = DruidDataSourceFactory.createDataSource(pros);
		
		Connection conn = source.getConnection();
		System.out.println(conn);
	}
	
}
