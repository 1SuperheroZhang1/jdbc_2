package com.atguigu3.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.atguigu1.utils.JDBCUtils;

/*
 * DAO:data(base) access object
 * ��װ����������ݱ��ͨ�ò���
 * */
public abstract class BaseDAO {
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
//						    String columnName = rsmd.getColumnName(i+1);
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
		//ͨ�õĲ�ѯ�����������������ݱ��еĶ�����¼���ɵļ���(version 2.0,����������)
		public <T> List<T> getForList(Connection conn,Class<T> clazz,String sql,Object ...args){
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
				
				//�������϶���
				ArrayList<T> list = new ArrayList<T>();
				while(rs.next()) {
					T t = clazz.newInstance();
					//��������һ�������е�ÿһ�У���t����ָ�������Ը�ֵ
					for(int i=0;i<columnCount;i++) {
						
						//��ȡÿ���е���ֵ��ͨ��ResultSet
						Object columnValue = rs.getObject(i+1);
						
						//ͨ��ResultSetMetaData
						//��ȡÿ���е�������getColumnName() -- ���Ƽ�ʹ��
						//��ȡÿ���еı�����getColumnLabel()
//						String columnName = rsmd.getColumnName(i+1);
						String columnLabel = rsmd.getColumnLabel(i+1);
						//��course����ָ����columnName���ԣ���ֵΪcolumnValue��ͨ������
						Field field = clazz.getDeclaredField(columnLabel);
						field.setAccessible(true);
						field.set(t, columnValue);
					}
					 list.add(t);
				}
				return list;
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtils.closeResource(null, ps, rs);
			}
			return null;
		}
		
		//���ڲ�ѯ����ֵ��ͨ�õķ���
		public <E> E getValue(Connection conn,String sql,Object ...args) {
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				ps = conn.prepareStatement(sql);
				
				for(int i=0;i<args.length;i++) {
					ps.setObject(i+1, args[i]);
				}
				
				rs = ps.executeQuery();
				if(rs.next()) {
					return (E) rs.getObject(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {				
				JDBCUtils.closeResource(null, ps, rs);
			}
			return null;
		}
}
