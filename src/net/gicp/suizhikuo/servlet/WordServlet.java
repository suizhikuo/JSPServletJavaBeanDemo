/**
 * 
 */
package net.gicp.suizhikuo.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.gicp.suizhikuo.dao.IPDao;
import net.gicp.suizhikuo.dao.WordDao;
import net.gicp.suizhikuo.toolsbean.MyTools;
import net.gicp.suizhikuo.valuebean.IPBean;
import net.gicp.suizhikuo.valuebean.WordBean;

/**
 * @author 智阔
 * 
 */
public class WordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public WordServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		request.setCharacterEncoding("UTF8");
		System.out.println("test--------------wordServlet.action=" + action);
		if (action == null) {
			action = "";

		}

		if (action.equals("readWord"))// 閱讀文章
			this.readWord(request, response);

		if (action.equals("addWord"))// 發表文章回覆
			this.addWord(request, response);
	}

	private void addWord(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		IPDao ipDao = new IPDao();
		IPBean ipBean = new IPBean();

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

		WordDao wordDao = new WordDao();
		WordBean wordBean = new WordBean();
		String content = request.getParameter("content");
		String author = request.getParameter("author");

		if (content != null)
			content = new String(content.getBytes("ISO-8859-1"), "UTF8");
		if (author != null)
			author = new String(author.getBytes("ISO-8859-1"), "UTF8");

		wordBean.setAuthor(author);
		wordBean.setContent(content);
		wordBean.setSdTime(sdTime);

		wordDao.addWord(wordBean);

		// HttpSession session = request.getSession();
		List wordList = wordDao.queryWord(1, 10);// 获取前10条留言
		session.setAttribute("wordList", wordList);

		RequestDispatcher rd = request
				.getRequestDispatcher("/front/word/Words.jsp");
		rd.forward(request, response);

	}

	public void readWord(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		IPDao ipDao = new IPDao();
		IPBean ipBean = new IPBean();

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

		WordDao wordDao = new WordDao();
		// HttpSession session = request.getSession();

		int begin;
		int count;
		String beginString = request.getParameter("wordBegin");// 分页显示时的第几页
		String countString = request.getParameter("wordCount");// 每页的留言数
		if (beginString == null)
			begin = 1;
		else
			begin = Integer.parseInt(beginString);
		if (countString == null)
			count = 10;
		else
			count = Integer.parseInt(countString);

		List wordList = wordDao.queryWord(begin, count);// 获取所有留言
		int wordSum = wordDao.queryWordSum();
		wordDao.getDB().close();// 关闭数据库连接
		session.setAttribute("wordSum", wordSum);
		session.setAttribute("wordList", wordList);

		RequestDispatcher rd = request
				.getRequestDispatcher("/front/word/Words.jsp");
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

}
