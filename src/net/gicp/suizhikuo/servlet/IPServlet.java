/**
 * 
 */
package net.gicp.suizhikuo.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.gicp.suizhikuo.dao.IPDao;
import net.gicp.suizhikuo.toolsbean.MyTools;
import net.gicp.suizhikuo.valuebean.IPBean;

/**
 * @author 智阔
 * 
 */
public class IPServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {

		IPDao ipDao = new IPDao();
		IPBean ipBean = new IPBean();

		// ipBean.setHeadersNames(request.getHeaderNames().toString());
		ipBean.setMethod(request.getMethod());
		ipBean.setProtocol(request.getProtocol());
		ipBean.setRealPath(request.getContextPath());// 可能需要修改
		ipBean.setReferer(request.getHeader("Referer"));
		if (request.getHeader("x-forwarded-for") == null) {
			ipBean.setRemoteAddr(request.getRemoteAddr());
		} else {
			ipBean.setRemoteAddr(request.getHeader("x-forwarded-for"));
		}
		ipBean.setRemoteHost(request.getRemoteHost());
		ipBean.setRequestUrl(request.getRequestURI());
		ipBean.setServerName(request.getServerName());
		ipBean.setServerPath(request.getServletPath());
		ipBean.setServerPort("" + request.getServerPort());

		HttpSession session = request.getSession();
		ipBean.setCharacterEncoding(request.getCharacterEncoding());
		ipBean.setQueryString(request.getQueryString());
		ipBean.setPathInfo(request.getPathInfo());
		ipBean.setRemoteUser(request.getRemoteUser());
		ipBean.setAcceptLanguage(request.getHeader("Accept-Language"));
		ipBean.setAcceptEncoding(request.getHeader("Accept-Encoding"));
		ipBean.setUserAgent(request.getHeader("User-Agent"));
		ipBean.setLastAccessed(session.getLastAccessedTime() + "");

		String sdTime = MyTools.ChangeTime(new Date());
		ipBean.setTime(sdTime);
		ipDao.addIP(ipBean);
		ipDao.getDB().close();// 关闭数据库连接

		Object obj = session.getAttribute("logoner");
		if (obj == null) {
			RequestDispatcher rd = request
					.getRequestDispatcher("/front/logon.jsp");
			rd.forward(request, resp);
		} else {
			int begin;
			int count;
			String beginString = request.getParameter("ipBegin");// 分页显示时的第几页
			String countString = request.getParameter("ipCount");// 每页的留言数
			if (beginString == null)
				begin = 1;
			else
				begin = Integer.parseInt(beginString);
			if (countString == null)
				count = 10;
			else
				count = Integer.parseInt(countString);

			List ipList = null;
			int ipSum = 0;
			try {
				ipList = ipDao.queryIP(begin, count);
				ipSum = ipDao.queryIPSum();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("ipList", ipList);// 获取全部ip记录
			session.setAttribute("ipSum", ipSum);

			ipDao.getDB().close();

			RequestDispatcher rd = request
					.getRequestDispatcher("/admin/AdminIndex.jsp");
			rd.forward(request, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

	/**
	 * 
	 */
	public IPServlet() {
		super();
	}

}
