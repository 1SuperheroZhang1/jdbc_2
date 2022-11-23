package com.atguigu1.transaction;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.junit.Test;

import com.atguigu1.utils.JDBCUtils;


/*
 * 1.ʲô�����ݿ�����
 * ����һ���߼�������Ԫ��ʹ���ݴ�һ��״̬�任����һ��״̬��
 * 		> һ���߼�������Ԫ��һ������DML������
 * 
 * 2.�������ԭ�򣺱�֤�������ﶼ��Ϊһ��������Ԫ��ִ�У���ʹ�����˹��ϣ������ܸı�����ִ�з�ʽ��
 * 	  ����һ��������ִ�ж������ʱ��Ҫô���е����ﶼ���ύ(commit)����ô��Щ�޸ľ����õر���������
 * Ҫô���ݿ����ϵͳ�����������������޸ģ���������ع�(rollback)�����״̬��
 * 
 * 3.����һ���ύ���Ͳ��ɻع���
 * 
 * 4.��Щ�����ᵼ�����ݵ��Զ��ύ��
 * 		>DDL����һ��ִ�У������Զ��ύ
 * 			>set autocommit=false��DDL������Ч
 *		>DMLĬ������£�һ��ִ�У��ͻ��Զ��ύ��
 *			>����ͨ��set autocommit=false�ķ�ʽȡ��DML�������Զ��ύ
 *		>Ĭ���ڹر�����ʱ�����Զ����ύ����
 * 
 * */
public class TransactionTest {
	
	
	//*********************δ�������ݿ������ת�˲���*************************
	/*
	 * ��������ݱ�user_table��˵��
	 * AA�û���BB�û�תװ100
	 * 
	 * update user_table set balance=balance-100 where user='AA';
	 * update user_table set balance=balance+100 where user='BB';
	 * */
	@Test
	public void testUpdate() {
		String sql1="update user_table set balance=balance-100 where user=?";
		update(sql1,"AA");
		
		//ģ�������쳣
		System.out.println(10/0);
		
		String sql2="update user_table set balance=balance+100 where user=?";
		update(sql2,"BB");
		
		System.out.println("ת�˳ɹ�");
	}
	//ͨ�õ���ɾ�Ĳ��� --- version 1.0
			public int update(String sql,Object ...args) {//sql��ռλ���ĸ�����ɱ��Բҵĳ�����ͬ��
				
				Connection conn=null;
				PreparedStatement ps=null;
				try {
					//1.��ȡ���ݿ������
					conn = JDBCUtils.getConnection();
					//2.Ԥ����sql��䣬����PreparedStatement��ʵ��
					ps = conn.prepareStatement(sql);
					//3.���ռλ��
					for(int i=0;i<args.length;i++) {
						ps.setObject(i+1, args[i]);//С�Ĳ�����������
					}
					//4.ִ��
					return ps.executeUpdate();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						//�޸���Ϊ�Զ��ύ����
						//��Ҫ�����ʹ�����ݿ����ӳص�ʹ��
						conn.setAutoCommit(true);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//5.��Դ�Ĺر�
					JDBCUtils.closeResource(conn, ps);
				}
				return 0;
			}
		
		//*****************************�������ݿ�����֮���ת�˲���******************************
		@Test
		public void testUpdateWithTx() {
			
			Connection conn=null;
			try {
				conn = JDBCUtils.getConnection();
				System.out.println(conn.getAutoCommit());//true
				//1.ȡ�����ݵ��Զ��ύ
				conn.setAutoCommit(false);
				
				String sql1="update user_table set balance=balance-100 where user=?";
				update(conn,sql1,"AA");
				
			//ģ�������쳣
//			System.out.println(10/0);
				
				String sql2="update user_table set balance=balance+100 where user=?";
				update(conn,sql2,"BB");
				
				System.out.println("ת�˳ɹ�");
				
				//2.�ύ����
				conn.commit();
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//3.�ع�����
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}finally {
				JDBCUtils.closeResource(conn, null);				
			}
		}
			//ͨ�õ���ɾ�Ĳ��� --- version 2.0(����������)
			public int update(Connection conn,String sql,Object ...args) {//sql��ռλ���ĸ�����ɱ��Բҵĳ�����ͬ��

				PreparedStatement ps=null;
				try {
					//1.Ԥ����sql��䣬����PreparedStatement��ʵ��
					ps = conn.prepareStatement(sql);
					//2.���ռλ��
					for(int i=0;i<args.length;i++) {
						ps.setObject(i+1, args[i]);//С�Ĳ�����������
					}
					//3.ִ��
					return ps.executeUpdate();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					
					try {
						//�޸���Ϊ�Զ��ύ����
						//��Ҫ�����ʹ�����ݿ����ӳص�ʹ��
						conn.setAutoCommit(true);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//4.��Դ�Ĺر�
					JDBCUtils.closeResource(null, ps);
				}
				return 0;
			}
		
//*************************************************************************
	@Test
	public void testTransactionSelect() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		//��ȡ��ǰ���ӵĸ��뼶��
		System.out.println(conn.getTransactionIsolation());
		//�������ݿ�ĸ��뼶��
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		//ȡ���Զ��ύ����
		conn.setAutoCommit(false);
		
		String sql="select user,password,balance from user_table where user=?";
		User user = getInstance(conn,User.class,sql,"CC");
		
		System.out.println(user);
	}
	@Test
	public void testTransactionUpdate() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		
		//ȡ���Զ��ύ����
		conn.setAutoCommit(false);
		String sql="update user_table set balance=? where user=?";
		update(conn,sql,5000,"CC");
		
		Thread.sleep(15000);
		System.out.println("�޸Ľ���");
	}
	//ͨ�õĲ�ѯ�����������������ݱ��е�һ����¼(version 2.0,����������)
	public <T> T getInstance(Connection conn,Class<T> clazz,String sql,Object ...args) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			  ps = conn.prepareStatement(sql);
			  for(int i=0;i<args.length;i++) {
					ps.setObject(i+1, args[i]);
			  }
					
			     rs = ps.executeQuery();
				//��ȡ�������Ԫ���ݣ�ResultSetMetaData
				ResultSetMetaData rsmd = rs.getMetaData();
				//ͨ��ResultSetMetaData��ȡ������е�����
				int columnCount = rsmd.getColumnCount();
					
				if(rs.next()) {
					T t = clazz.newInstance();
						
					for(int i=0;i<columnCount;i++) {
							
					//��ȡÿ���е���ֵ��ͨ��ResultSet
						Object columnValue = rs.getObject(i+1);
							
					//ͨ��ResultSetMetaData
					//��ȡÿ���е�������getColumnName() -- ���Ƽ�ʹ��
					//��ȡÿ���еı�����getColumnLabel()
//					    String columnName = rsmd.getColumnName(i+1);
						String columnLabel = rsmd.getColumnLabel(i+1);
					//��course����ָ����columnName���ԣ���ֵΪcolumnValue��ͨ������
						Field field = clazz.getDeclaredField(columnLabel);
						field.setAccessible(true);
						field.set(t, columnValue);
					}
					   return t;
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					JDBCUtils.closeResource(null, ps, rs);
				}
				return null;
			}
}
