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

<title>My JSP 'AddArticle.jsp' starting page</title>

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
	<center>
		<form action="ArticleServlet?action=addArticleConfig" method="post">
			文章类型： <select name="typeId" size="1">
				<%
					HashMap<Integer, String> hm = (HashMap<Integer, String>) session
							.getAttribute("hm");
					Iterator<Integer> iterator = hm.keySet().iterator();
					int temp;
					while (iterator.hasNext()) {
						temp = iterator.next();
				%>
				<option value=<%=temp%>>
					<%=hm.get(temp)%></option>
				<%
					}
				%>
			</select><br /> 标题：<input type="text" name="title" value="请输入你的标题" /><br>
			文章说明：<input type="text" name="info" value="文章信息" /><br> create：<input
				type="text" name="create" value="create" /><br>
			<textarea name="content" rows="60" cols="60">文章正文</textarea>
			<br> <input type="submit" name="submit1" value="发表" />
		</form>
	</center>
</body>
</html>
