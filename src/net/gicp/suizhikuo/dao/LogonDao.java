package net.gicp.suizhikuo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.gicp.suizhikuo.valuebean.MasterBean;

 
public class LogonDao {
	private DB connection = null;

	public LogonDao() {
		connection = new DB();
	}

	public DB getDB() {

		return connection;
	}

	public boolean logon(MasterBean logoner) {
		boolean flag = false;
		String masterName = logoner.getMasterName();
		String masterPassword = logoner.getMasterPass();
		String sql = "select * from tb_master where master_name ='"
				+ masterName + "' and master_password ='" + masterPassword
				+ "'";

		ResultSet rs = connection.executeQuery(sql);

		System.out.println("test-------------LogonDao.logon.rs=" + rs);
		try {
			if (rs.next())
				flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
}
