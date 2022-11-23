package com.atguigu5.dbutils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.atguigu2.bean.Customer;
import com.atguigu4.utils.JDBCUtils;

/*
 * commons-dbutils ��Apache��֯�ṩ��һ����ԴJDBC������⣬��װ����������ݿ����ɾ�Ĳ����
 * */
public class QueryRunnerTest {

	//���Բ���
	@Test
	public void testInsert() {
		Connection conn=null;
		try {
			QueryRunner runner = new QueryRunner();
			
			conn = JDBCUtils.getConnection3();
			String sql="insert into customer(name,email,birth) values(?,?,?)";
			int insertCount = runner.update(conn, sql, "������","caixukun@126.com","1997-08-09");
			System.out.println("�����"+insertCount+"����¼");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
		
	}
	
	//���Բ�ѯ
	/*
	 * BeanHandler:��ResultSetHandler�ӿڵ�ʵ���࣬���ڷ�װ���е�һ����¼��
	 * */
	@Test
	public void testQuery1()  {
		Connection conn=null;
		try {
			QueryRunner runner = new QueryRunner();
			conn = JDBCUtils.getConnection3();
			String sql="select id,name,email from customer where id=?";
			BeanHandler<Customer> handler=new BeanHandler<>(Customer.class);
			Customer customer = runner.query(conn, sql, handler, 1);
			System.out.println(customer);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}
	
	/*
	 * BeanListHandler:��ResultSetHandler�ӿڵ�ʵ���࣬���ڷ�װ���еĶ�����¼���ɵļ���
	 * */
	@Test
	public void testQuery2()  {
		Connection conn=null;
		try {
			QueryRunner runner = new QueryRunner();
			conn = JDBCUtils.getConnection3();
			String sql="select id,name,email from customer";
			BeanListHandler<Customer> handler=new BeanListHandler<>(Customer.class);
			 List<Customer> list = runner.query(conn, sql, handler);
			 list.forEach(System.out::println);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}
	
	/*
	 * MapHandler:��ResultSetHandler�ӿڵ�ʵ���࣬��Ӧ���е�һ����¼��
	 * ���ֶμ���Ӧ�ֶε�ֵ��Ϊmap�е�key��value
	 * */
	@Test
	public void testQuery3()  {
		Connection conn=null;
		try {
			QueryRunner runner = new QueryRunner();
			conn = JDBCUtils.getConnection3();
			String sql="select id,name,email from customer where id=?";
			MapHandler handler=new MapHandler();
			Map<String,Object> map = runner.query(conn, sql, handler,1);
			System.out.println(map);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}
	
	/*
	 * MapListHandler:��ResultSetHandler�ӿڵ�ʵ���࣬��Ӧ���еĶ�����¼��
	 * ���ֶμ���Ӧ�ֶε�ֵ��Ϊmap�е�key��value������Щmap��ӵ�List��
	 * */
	@Test
	public void testQuery4()  {
		Connection conn=null;
		try {
			QueryRunner runner = new QueryRunner();
			conn = JDBCUtils.getConnection3();
			String sql="select id,name,email from customer where id<?";
			MapListHandler handler=new MapListHandler();
			List<Map<String,Object>> list = runner.query(conn, sql, handler,4);
			list.forEach(System.out::println);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}
	
	/*
	 * ScalarHandler:���ڲ�ѯ����ֵ
	 * */
	@Test
	public void testQuery5()  {
		Connection conn=null;
		try {
			QueryRunner runner = new QueryRunner();
			conn = JDBCUtils.getConnection3();
			String sql="select count(*) from customer";
			ScalarHandler handler=new ScalarHandler();
			Long count = (Long) runner.query(conn, sql, handler);
			System.out.println(count);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}
	@Test
	public void testQuery6()  {
		Connection conn=null;
		try {
			QueryRunner runner = new QueryRunner();
			conn = JDBCUtils.getConnection3();
			String sql="select max(birth) from customer";
			ScalarHandler handler=new ScalarHandler();
			Object maxBirth = runner.query(conn, sql, handler);
			System.out.println(maxBirth);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}
	
	/*
	 * �Զ���ResultSetHandler��ʵ����
	 * */
	@Test
	public void testQuery7()  {
		Connection conn=null;
		try {
			QueryRunner runner = new QueryRunner();
			conn = JDBCUtils.getConnection3();
			String sql="select id,name,email,birth from customer where id=?";
			
			//����ʵ����
			ResultSetHandler<Customer> handler=new ResultSetHandler<Customer>() {

				@Override
				public Customer handle(ResultSet rs) throws SQLException {
					if(rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						String email = rs.getString("email");
						Date birth = rs.getDate("birth");
						Customer customer = new Customer(id,name,email,birth);
						return customer;
					}
					return null;
				}
				
			};
			runner.query(conn, sql, handler,3);
			System.out.println();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}
}
