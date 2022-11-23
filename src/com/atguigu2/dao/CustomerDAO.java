package com.atguigu2.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.atguigu2.bean.Customer;

/*
 * �˽ӿ����ڹ淶�����customer��ĳ��ò���
 * */
public interface CustomerDAO {
	/**
	 * @Description ��cust������ӵ����ݿ���
	 * @author ����ɭ
	 * @param conn
	 * @param cust
	 * */
	void insert(Connection conn,Customer cust);
	/**
	 * @Description ���ָ����id��ɾ�����е�һ����¼
	 * @author ����ɭ
	 * @param conn
	 * @param id
	 * */
	void deleteById(Connection conn,int id);
	/**
	 * @Description ����ڴ��е�cust����ȥ�޸����ݱ���ָ���ļ�¼
	 * @author ����ɭ
	 * @param conn
	 * @param cust
	 * */
	void update(Connection conn,Customer cust);
	/**
	 * @Description ���ָ����id��ѯ�õ���Ӧ��Customer����
	 * @author ����ɭ
	 * @param conn
	 * @param id
	 * */
	Customer getCustomerById(Connection conn,int id);
	/**
	 * @Description ��ѯ�������м�¼�ļ���
	 * @author ����ɭ
	 * @param conn
	 * @return
	 * */
	List<Customer> getAll(Connection conn);
	/**
	 * @Description �������ݱ��е����ݵ���Ŀ��
	 * @author ����ɭ
	 * @param conn
	 * @return
	 * */
	Long getCount(Connection conn);
	/**
	 * @Description �������ݱ�����������
	 * @author ����ɭ
	 * @param conn
	 * @return
	 * */
	Date getMaxBirth(Connection conn);
}
