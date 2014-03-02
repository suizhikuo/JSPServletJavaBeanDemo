package net.gicp.suizhikuo.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 



import net.gicp.suizhikuo.dao.ArticleDao;
import net.gicp.suizhikuo.dao.IPDao;
import net.gicp.suizhikuo.dao.ReviewDao;
import net.gicp.suizhikuo.toolsbean.MyTools;
import net.gicp.suizhikuo.valuebean.ArticleBean;
import net.gicp.suizhikuo.valuebean.IPBean;
import net.gicp.suizhikuo.valuebean.ReviewBean;

public class ArticleServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ArticleServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
		
	}

	public void init() throws ServletException {
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}

		// System.out.println("test-------------------------------------------_ArticleServlet.doPost.read");

		if (action.equals("all")) { // 分页显示所有文章
			this.Articles(request, response);
		}

		if (action.equals("read"))// 閱讀文章
			this.readArticle(request, response);

		if (action.equals("followAdd"))// 發表文章回覆
			this.validateFollow(request, response);

		if (action.equals("addArticle")) { // 转跳到添加文章页面
			this.addArticles(request, response);
		}
		if (action.equals("addArticleConfig")) { // 确认添加文章
			this.addArticlesConfig(request, response);
		}

	}

	public void Articles(HttpServletRequest request,
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

		String sdTime = MyTools.ChangeTime(new Date());
		ipBean.setTime(sdTime);
		ipDao.addIP(ipBean);
		ipDao.getDB().close();// 关闭数据库连接

		int begin;
		int count;
		String beginString = request.getParameter("articleBegin");// 分页显示时的第几页
		String countString = request.getParameter("articleCount");// 每页的留言数
		if (beginString == null)
			begin = 1;
		else
			begin = Integer.parseInt(beginString);
		if (countString == null)
			count = 10;
		else
			count = Integer.parseInt(countString);

		HttpSession session = request.getSession();
		ArticleDao articleDao = new ArticleDao();
		List articleList = articleDao.queryArticleFromTo(begin, count);// 获取所有留言
		System.out.println("test---------------articleList.size=:"
				+ articleList.size());
		int articleSum = articleDao.queryArticleSum();
		articleDao.getDB().close();// 关闭数据库连接
		session.setAttribute("articleSum", articleSum);
		session.setAttribute("articleList", articleList);

		RequestDispatcher rd = request
				.getRequestDispatcher("/front/article/AllArticles.jsp");
		rd.forward(request, response);
	}

	public void readArticle(HttpServletRequest request,
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

		// HttpSession session = request.getSession();
		ArticleBean articleBean = new ArticleBean();
		ArticleDao articleDao = new ArticleDao();
		ReviewDao reviewDao = new ReviewDao(); // 創建一個對文章評論進行操作的對象

		String strId = request.getParameter("id");
		int id = MyTools.strToint(strId);
		articleBean.setId(id);

		boolean flag = articleDao.operationArticle("readTimes", articleBean);// 累加閱讀次數
		if (flag)
			System.out.println("test:增加阅读次数成功");
		else
			System.out.println("test:增加阅读次数失败");

		articleBean = articleDao.queryArticleSingle(id);// 獲取指定文章的內容
		session.setAttribute("readSingle", articleBean);// 保存article到request中

		int begin;
		int count;
		String beginString = request.getParameter("reviewBegin");// 分页显示时的第几页
		String countString = request.getParameter("reviewCount");// 每页的留言数
		if (beginString == null)
			begin = 1;
		else
			begin = Integer.parseInt(beginString);
		if (countString == null)
			count = 10;
		else
			count = Integer.parseInt(countString);

		List reviewList = reviewDao.queryReview(id, begin, count); // 獲取指定文章的評論
		int reviewSum = reviewDao.queryReviewSum(id);
		session.setAttribute("reviewSum", reviewSum);
		session.setAttribute("reviewList", reviewList);
		RequestDispatcher rd = request
				.getRequestDispatcher("/front/article/ArticleSingle.jsp");
		rd.forward(request, response);
	}

	// 發表文章回覆
	public void validateFollow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
		String sdTime = MyTools.ChangeTime(new Date());
		ipBean.setTime(sdTime);
		ipDao.addIP(ipBean);
		ipDao.getDB().close();// 关闭数据库连接

		String strId = request.getParameter("articleId");
		int articleId = MyTools.strToint(strId);
		String content = request.getParameter("content");
		String author = request.getParameter("author");

		if (content != null)
			content = new String(content.getBytes("ISO-8859-1"), "UTF8");// 过滤器转码
		if (author != null)
			author = new String(author.getBytes("ISO-8859-1"), "UTF8");// 过滤器转码

		ReviewBean reviewBean = new ReviewBean();
		reviewBean.setArticleId(articleId);
		reviewBean.setAuthor(author);
		reviewBean.setContent(content);
		reviewBean.setSdTime(sdTime);
		ReviewDao reviewDao = new ReviewDao();
		reviewDao.followAdd(reviewBean);
		HttpSession session = request.getSession();
		ArticleBean articleBean = new ArticleBean();
		articleBean.setId(articleId);

		ArticleDao articleDao = new ArticleDao();
		articleDao.operationArticle("readTimes", articleBean);// 累加閱讀次數
		articleBean = articleDao.queryArticleSingle(articleId);// 獲取指定文章的內容
		session.setAttribute("readSingle", articleBean);// 保存article到request中

		int begin;
		int count;
		String beginString = request.getParameter("reviewBegin");// 分页显示时的第几页
		String countString = request.getParameter("reviewCount");// 每页的留言数
		if (beginString == null)
			begin = 1;
		else
			begin = Integer.parseInt(beginString);
		if (countString == null)
			count = 10;
		else
			count = Integer.parseInt(countString);

		List reviewList = reviewDao.queryReview(articleId, begin, count); // 獲取指定文章的評論
		session.setAttribute("reviewList", reviewList);
		System.out
				.println("test-------------------------------------------_ArticleServlet.readArticle");
		RequestDispatcher rd = request
				.getRequestDispatcher("/front/article/ArticleSingle.jsp");
		rd.forward(request, response);
	}

	// 转跳到发表文章页面
	public void addArticles(HttpServletRequest request,
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
		String sdTime = MyTools.ChangeTime(new Date());
		ipBean.setTime(sdTime);
		ipDao.addIP(ipBean);
		ipDao.getDB().close();// 关闭数据库连接
		
		ArticleDao ad = new ArticleDao();
		HashMap<Integer,String> hm = new HashMap<Integer,String>();
		hm = ad.queryArticleStyle();
		HttpSession session = request.getSession();
		session.setAttribute("hm", hm);
		ad.getDB().close();

		RequestDispatcher rd = request.getRequestDispatcher("/admin/AddArticle.jsp");
		rd.forward(request, response);
	}
	
	public void addArticlesConfig(HttpServletRequest request,
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
		String sdTime = MyTools.ChangeTime(new Date());
		ipBean.setTime(sdTime);
		ipDao.addIP(ipBean);
		ipDao.getDB().close();// 关闭数据库连接
		
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String create = request.getParameter("create");
		String info = request.getParameter("info");
		//String sdTime = MyTools.ChangeTime(new Date();
		
		if(title != null)
			title = new String(title.getBytes("ISO-8859-1"),"UTF8");//过滤器转码
		if(content != null)
			content = new String(content.getBytes("ISO-8859-1"),"UTF8");
		if(info != null)
			info = new String(info.getBytes("ISO-8859-1"),"UTF8");//过滤器转码
		if(create != null)
			create = new String(create.getBytes("ISO-8859-1"),"UTF8");
			
		ArticleBean ab = new ArticleBean();
		ab.setContent(content);
		ab.setCreate(create);
		ab.setInfo(info);
		ab.setSdTime(sdTime);
		ab.setTitle(title);
		ab.setTypeId(Integer.valueOf(request.getParameter("typeId")));
		
		ArticleDao ad = new ArticleDao();
		Boolean flag = ad.operationArticle("add", ab);
		System.out.println("test----------------------->addAtricleConfig.flag="+flag);
		ad.getDB().close();
		
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}
	
}
