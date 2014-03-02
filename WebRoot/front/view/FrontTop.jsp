<%@ page language="java" import="java.util.*" pageEncoding="UTF8"%>
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

<title>FrontTop.jsp</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>
<%
	Object atr = session.getAttribute("myArticlePercent");
	Object fri = session.getAttribute("myFriendPercent");
	Object word = session.getAttribute("myWordPercent");
	String myAtriclePercent;
	String myFriendPercent;
	String myWordPercent;

	if (atr != null)
		myAtriclePercent = atr.toString();
	else
		myAtriclePercent = "70.5";
	if (fri != null)
		myFriendPercent = fri.toString();
	else
		myFriendPercent = "13.9";
	if (word != null)
		myWordPercent = word.toString();
	else
		myWordPercent = "15.6";
%>

<body>
	<img src="<%=basePath%>images/cs.jpg" style="float:left" />
	<div style="float:left; margin-left:25px">
		<h3>
			<%=myAtriclePercent%>%的人对【我的文章】感兴趣 <br><%=myFriendPercent%>%的人对【我的好友】感兴趣
			<br>
			<%=myWordPercent%>%的人对【给我留言】感兴趣<br>
		</h3>
	</div>
	<div style="float:left; margin-left:45px">
		<h3></h3>
	</div>
	<table width="100%" height="40%" bgcolor="aabbaa" align="center"
		cellspacing="20%">
		<tr>
			<td><a href="<%=path%>/IndexServlet">博客首页</a></td>
			<td><a href="<%=path%>/ArticleServlet?action=all">我的文章</a></td>
			<td><a href="<%=path%>/FriendServlet?begin=1&count=10">我的好友</a></td>
			<td><a href="<%=path%>/WordServlet?action=readWord">给我留言</a></td>
		</tr>
	</table>
</body>
</html>
