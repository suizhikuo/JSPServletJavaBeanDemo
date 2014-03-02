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

import net.gicp.suizhikuo.dao.ArticleDao;
import net.gicp.suizhikuo.dao.FriendDao;
import net.gicp.suizhikuo.dao.IPDao;
import net.gicp.suizhikuo.dao.WordDao;
import net.gicp.suizhikuo.toolsbean.MyTools;
import net.gicp.suizhikuo.valuebean.IPBean;
import net.gicp.suizhikuo.valuebean.MasterBean;

public class IndexServlet extends HttpServlet {

	private static MasterBean masterBean;

	public IndexServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		/********************* 获取在主页面的内容显示区中的内容 ****************/
		ArticleDao articleDao = new ArticleDao();

		int[] percent = new int[3];
		double sum = 0;
		String myArticlePercent = "", myFriendPercent = "", myWordPercent = "";

		try {
			percent = ipDao.queryIPPercent();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = percent.length - 1; i >= 0; i--) {
			System.out
					.println("test---------------------->IndexServlet.percent[]"
							+ percent[i]);
			sum += percent[i];
		}
		myArticlePercent = String.format("%.2f", percent[0] * 100.0 / sum);
		myFriendPercent = String.format("%.2f", percent[1] * 100.0 / sum);
		myWordPercent = String.format("%.2f", percent[2] * 100.0 / sum);

		session.setAttribute("myArticlePercent", myArticlePercent);
		session.setAttribute("myFriendPercent", myFriendPercent);
		session.setAttribute("myWordPercent", myWordPercent);

		// 从tb_article数据表中获取前3篇文章
		List articleList = articleDao.queryArticle(-1, null);// 不按文章类别，查询前3篇文章
		request.setAttribute("articleList", articleList);

		WordDao wordDao = new WordDao();
		int begin = 1;
		int count = 3;

		List iWordList = wordDao.queryWord(begin, count);// 获取所有留言
		int wordSum = wordDao.queryWordSum();

		session.setAttribute("wordSum", wordSum);
		session.setAttribute("iWordList", iWordList);

		FriendDao friendDao = new FriendDao();
		List iFriendList = friendDao.queryFriend(begin, count);
		session.setAttribute("iFriendList", iFriendList);

		// ipDao.getDB().close();
		wordDao.getDB().close();// 关闭数据库连接
		friendDao.getDB().close();
		articleDao.getDB().close();

		RequestDispatcher rd = request
				.getRequestDispatcher("/front/FrontIndex.jsp");
		rd.forward(request, response);
	}

}
