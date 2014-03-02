package net.gicp.suizhikuo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

	// 数据库连接RUL
	private String url = "jdbc:mysql://localhost:3306/db_blog";
	private String userName = "root";
	private String password = "Admin123456";

	// 数据量连接变量
	private String className = "com.mysql.jdbc.Driver";
	private Connection con = null;
	private Statement stm = null;

	public DB() {
		try {
			Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库驱动包加载失败！");
		}
	}

	public void createCon() {
		try {
			if (con == null) {

				con = DriverManager.getConnection(url, userName, password);
			} else {

			}
		} catch (SQLException e) {
			System.out.println("获取数据库连接失败！");
		}
	}

	public void getStm() {
		createCon();
		try {
			stm = con.createStatement();
		} catch (Exception e) {
			System.out.println("创建Statement失败！");
		}
	}

	public boolean executeUpdate(String sql) {
		boolean mark = false;
		try {
			getStm();
			int iCount = stm.executeUpdate(sql);
			if (iCount > 0)
				mark = true;
			else
				mark = false;
		} catch (Exception e) {

			System.out.println("执行sql语句失败！");
			mark = false;
		}
		return mark;
	}

	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			getStm();
			try {
				rs = stm.executeQuery(sql);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("执行sql语句失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void close() {

	}
}
