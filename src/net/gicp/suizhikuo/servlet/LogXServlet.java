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
import net.gicp.suizhikuo.dao.LogonDao;
import net.gicp.suizhikuo.toolsbean.MyTools;
import net.gicp.suizhikuo.valuebean.IPBean;
import net.gicp.suizhikuo.valuebean.MasterBean;

public class LogXServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null)
			action = "";
		if (action.equals("islogon"))
			this.isLogon(request, response);
		if (action.equals("logon"))
			this.logon(request, response);
		if (action.equals("logout"))
			this.logout(request, response);
	}

	public void isLogon(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

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
		// ipBean.setLastAccessed(request.getHeader("Last-Accessed"));
		ipBean.setLastAccessed(session.getLastAccessedTime() + "");

		String sdTime = MyTools.ChangeTime(new Date());
		ipBean.setTime(sdTime);
		ipDao.addIP(ipBean);

		String forward = "";
		// HttpSession session = request.getSession();
		if (session.getAttribute("logoner") != null)
			forward = "/admin/AdminIndex.jsp";
		else
			forward = "/front/logon.jsp";
		response.sendRedirect(forward);
	}

	public boolean validateLogon(HttpServletRequest request,
			HttpServletResponse response) {
		boolean mark = true;
		String messages = "";
		String name = request.getParameter("userName");
		String password = request.getParameter("userPass");
		if (name == null || name.equals("")) {
			mark = false;
			messages += "<li>请输入<b>用户名!</b></li>";
		}
		if (password == null || password.equals("")) {
			mark = false;
			messages += "<li>请输入<b>密码!</b></li>";
		}
		request.setAttribute("messages", messages);
		return mark;
	}

	public void logon(HttpServletRequest request, HttpServletResponse response)
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
		boolean flag = validateLogon(request, response);
		RequestDispatcher rd = null;
		if (flag) {
			LogonDao masterDao = new LogonDao();
			MasterBean logoner = new MasterBean();
			logoner.setMasterName(request.getParameter("userName"));
			logoner.setMasterPass(request.getParameter("userPass"));
			boolean mark = masterDao.logon(logoner);// 验证用户身份

			System.out
					.println("test------------------LogXServerlet.logon:mark:"
							+ mark);
			if (!mark) { // 不存在该用户
				System.out.println("test------------------LogXServerlet.logon:"
						+ "if不存在该用户,logoner.masterName="
						+ logoner.getMasterName() + "logoner.pass="
						+ logoner.getMasterPass());
				request.setAttribute("messages", "<li>输入的用户名或密码错误!</li>");
				rd = request.getRequestDispatcher("front/logon.jsp");
				rd.forward(request, response);
			} else {
				System.out
						.println("test------------------LogXServerlet.logon:else存在该用户");

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
				session.setAttribute("logoner", logoner);// 将当前登陆的用户注册到session中的logoner属性中

				ipDao.getDB().close();
				response.sendRedirect("admin/AdminIndex.jsp");
			}
		} else { // 为空
			rd = request.getRequestDispatcher("/front/logon.jsp");
			rd.forward(request, response);
		}
	}

	public void logout(HttpServletRequest request, HttpServletResponse response)
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

		// HttpSession session = request.getSession();
		session.removeAttribute("logoner");
		RequestDispatcher rd = request
				.getRequestDispatcher("/front/FrontIndex.jsp");
		rd.forward(request, response);
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

	public LogXServlet() {
		super();
	}

}
