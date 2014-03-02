<%@ page language="java" import="java.util.*" pageEncoding="UTF8"%>
<%@page import="net.gicp.suizhikuo.valuebean.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'AdminIndex.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<a href="ArticleServlet?action=addArticle"><h2>1、发表文章</h2></a>


	<h2>客户端访问记录：</h2>
	<%
		Object logoner = session.getAttribute("logoner");
		if (logoner == null) {
	%>
	<jsp:forward page="logon.jsp" />
	<%
		}

		List ipList = (List) session.getAttribute("ipList");//获取全部ip记录
		int ipSum = Integer.parseInt(session.getAttribute("ipSum")
				.toString());
	%>
	<table border="1">
		<tr>
			<td>id</td>
			<td>用户代理</td>
			<td>上一访问页面</td>
			<td>请求的方法</td>
			<td>协议</td>
			<td>请求的URL</td>
			<td>当前文件绝对路径</td>
			<td>客户端地址</td>
			<td>客户端名</td>
			<td>服务器名</td>
			<td>客户端所请求的脚本文件的文件路径</td>
			<td>服务器端口</td>
			<td>请求的时间</td>
			<td>客户端字符编码</td>
			<td>查询的字符串</td>
			<td>路径信息</td>
			<td>远程用户</td>
			<td>客户端接收的语言</td>
			<td>客户端接收的编码</td>
			<td>上一次访问时间</td>
		</tr>
		<%
			int i = 0;
			IPBean ipBean;
			while (i < ipList.size()) {
				ipBean = (IPBean) ipList.get(i);
		%>
		<tr>
			<td><%=ipBean.getId()%></td>
			<td><%=ipBean.getUserAgent()%></td>
			<td><%=ipBean.getReferer()%></td>
			<td><%=ipBean.getMethod()%></td>
			<td><%=ipBean.getProtocol()%></td>
			<td><%=ipBean.getRequestUrl()%></td>
			<td><%=ipBean.getRealPath()%></td>
			<td><%=ipBean.getRemoteAddr()%></td>
			<td><%=ipBean.getRemoteHost()%></td>
			<td><%=ipBean.getServerName()%></td>
			<td><%=ipBean.getServerPath()%></td>

			<td><%=ipBean.getServerPort()%></td>
			<td><%=ipBean.getTime()%></td>
			<td><%=ipBean.getCharacterEncoding()%></td>
			<td><%=ipBean.getQueryString()%></td>
			<td><%=ipBean.getPathInfo()%></td>
			<td><%=ipBean.getRemoteUser()%></td>
			<td><%=ipBean.getAcceptLanguage()%></td>
			<td><%=ipBean.getAcceptEncoding()%></td>
			<td>
				<%
					SimpleDateFormat format = new SimpleDateFormat(
								"yyyy年MM月dd日 HH:mm:ss");
						try {
							Date date = new Date(Long.parseLong(ipBean
									.getLastAccessed()));
							out.print(format.format(date));
						} catch (Exception e) {
							e.printStackTrace();
						}
				%>
			</td>
		</tr>
		<%
			i++;
			}
			out.println("</table>");

			for (int j = 0; j < (ipSum + 9) / 10; j++) {
		%>
		<a href="IPServlet?ipBegin=<%=j + 1%>&ipCount=<%=10%>"><%=j + 1%></a>
		<%
			if (j != 0 && j % 10 == 0)
					out.println(" <br/>");
		%>

		<%
			}
		%>
	
</body>
</html>
