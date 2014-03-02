package net.gicp.suizhikuo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.gicp.suizhikuo.valuebean.IPBean;

public class IPDao {
	private DB connection = null;
	private IPBean ipBean = null;

	public IPDao() {
		connection = new DB();
	}

	public DB getDB() {

		return connection;
	}

	public int queryIPSum() throws SQLException {
		String sql = "select count(*) from ip";
		int sum = 0;
		ResultSet rs = connection.executeQuery(sql);
		if (rs != null) {
			try {
				rs.next();
				sum = rs.getInt(1);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rs.close();
		return sum;
	}

	public List queryIP(int begin, int count) throws SQLException {
		List ipList = new ArrayList();
		String sql = "";
		if (begin == 0 && count == 0) {
			sql = "select * from ip order by id desc";
		} else
			sql = "select * from ip order by id desc limit " + (begin - 1) * 10
					+ "," + count + "";

		ResultSet rs = connection.executeQuery(sql);
		if (rs != null) {
			try {
				while (rs.next()) {
					ipBean = new IPBean();
					ipBean.setId(rs.getInt(1));
					ipBean.setReferer(rs.getString(2));
					ipBean.setMethod(rs.getString(3));
					ipBean.setProtocol(rs.getString(4));
					ipBean.setRequestUrl(rs.getString(5));
					ipBean.setRealPath(rs.getString(6));
					ipBean.setRemoteAddr(rs.getString(7));
					ipBean.setRemoteHost(rs.getString(8));
					ipBean.setServerName(rs.getString(9));
					ipBean.setServerPath(rs.getString(10));
					ipBean.setServerPort(rs.getString(11));
					ipBean.setTime(rs.getString(12));
					ipBean.setCharacterEncoding(rs.getString(13));// 第十三个字段
					ipBean.setQueryString(rs.getString(14));
					ipBean.setPathInfo(rs.getString(15));
					ipBean.setRemoteUser(rs.getString(16));
					ipBean.setAcceptLanguage(rs.getString(17));
					ipBean.setAcceptEncoding(rs.getString(18));
					ipBean.setLastAccessed(rs.getString(19));
					ipBean.setUserAgent(rs.getString(20));
					ipList.add(ipBean);

					System.out
							.println("test-------------------WordDao.queryWord()查询留言成功");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		rs.close();
		return ipList;
	}

	public int[] queryIPPercent() throws SQLException, Exception {
		List ipList = new ArrayList();
		String sql = "select serverPath from ip order by id desc";
		int percent[] = new int[3];
		int myWord = 0;
		int myFriend = 0;
		int myArticle = 0;
		String temp = "";
		ResultSet rs = connection.executeQuery(sql);
		if (rs != null) {
			try {
				while (rs.next()) {
					temp = rs.getString("serverPath");
					if (temp.equalsIgnoreCase("/WordServlet"))
						myWord++;
					if (temp.equalsIgnoreCase("/FriendServlet"))
						myFriend++;
					if (temp.equalsIgnoreCase("/ArticleServlet"))
						myArticle++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		rs.close();
		percent[0] = myArticle;
		percent[1] = myFriend;
		percent[2] = myWord;
		return percent;
	}

	public void addIP(IPBean ipBean) {
		String referer = ipBean.getReferer();
		if (referer == null)
			referer = "default";
		String method = ipBean.getMethod();
		String protocol = ipBean.getProtocol();
		String requestUrl = ipBean.getRequestUrl();
		String realPath = ipBean.getRealPath();
		String remoteAddr = ipBean.getRemoteAddr();
		String remoteHost = ipBean.getRemoteHost();
		String serverName = ipBean.getServerName();
		String serverPath = ipBean.getServerPath();
		String serverPort = ipBean.getServerPort();
		String time = ipBean.getTime();

		String characterEncoding = ipBean.getCharacterEncoding();
		if (characterEncoding == null)
			characterEncoding = "default";
		String queryString = ipBean.getQueryString();
		if (queryString == null)
			queryString = "default";
		String pathInfo = ipBean.getPathInfo();
		if (pathInfo == null)
			pathInfo = "default";
		String remoteUser = ipBean.getRemoteUser();
		if (remoteUser == null)
			remoteUser = "default";
		String acceptLanguage = ipBean.getAcceptLanguage();
		if (acceptLanguage == null)
			acceptLanguage = "default";
		String acceptEncoding = ipBean.getAcceptEncoding();
		if (acceptEncoding == null)
			acceptEncoding = "default";
		String userAgent = ipBean.getUserAgent();
		if (userAgent == null)
			userAgent = "default";
		String lastAccessed = ipBean.getLastAccessed();
		if (lastAccessed == null)
			lastAccessed = "default";

		System.out.println("length:" + referer + "***" + "***" + method + "***"
				+ protocol + "***" + requestUrl + "***" + realPath + "***"
				+ remoteAddr + "***" + remoteHost + "***" + serverName + "***"
				+ serverPath + "***" + serverPort + "***" + time + "***"
				+ characterEncoding + "***" + queryString + "***" + pathInfo
				+ "***" + remoteUser + "***" + acceptLanguage + "***"
				+ acceptEncoding + "***" + userAgent + "***" + lastAccessed);

		System.out.println("length:" + referer.length() + "***" + "***"
				+ method.length() + "***" + protocol.length() + "***"
				+ requestUrl.length() + "***" + realPath.length() + "***"
				+ remoteAddr.length() + "***" + remoteHost.length() + "***"
				+ serverName.length() + "***" + serverPath.length() + "***"
				+ serverPort.length() + "***" + time.length() + "***"
				+ characterEncoding.length() + "***" + queryString.length()
				+ "***" + pathInfo.length() + "***" + remoteUser.length()
				+ "***" + acceptLanguage.length() + "***"
				+ acceptEncoding.length() + "***" + userAgent.length() + "***"
				+ lastAccessed.length());

		String sql = "insert into ip(referer ,method ,protocol,requestUrl,realPath,remoteAddr,remoteHost,"
				+ "serverName,serverPath,serverPort,time,characterEncoding,queryString,pathInfo,remoteUser,acceptLanguage,acceptEncoding,userAgent,lastAccessed )"
				+ " values('"
				+ referer
				+ "','"
				+ method
				+ "','"
				+ protocol
				+ "','"
				+ requestUrl
				+ "','"
				+ realPath
				+ "'"
				+ ",'"
				+ remoteAddr
				+ "','"
				+ remoteHost
				+ "','"
				+ serverName
				+ "','"
				+ serverPath
				+ "','"
				+ serverPort
				+ "','"
				+ time
				+ "','"
				+ characterEncoding
				+ "','"
				+ queryString
				+ "','"
				+ pathInfo
				+ "','"
				+ remoteUser
				+ "','"
				+ acceptLanguage
				+ "','"
				+ acceptEncoding
				+ "','"
				+ userAgent + "','" + lastAccessed + "')";

		boolean flag = connection.executeUpdate(sql);

		if (flag == true)
			System.out.println("test-----------IPDao.addIP成功");
		else
			System.out.println("test-----------IPDao.addIP失败");
	}

}
