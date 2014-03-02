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

<title>My JSP 'logon.jsp' starting page</title>

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
	String messages = (String) request.getAttribute("messages");
	if (messages == null) {
		messages = "";
	}
%>

<body>
	<table align="center" width="50">
		<!-- 大表格 -->
		<tr>
			<td align="center">
				<table border="0" style="margin-top:300" align="center" width="50">
					<!-- 小表格 -->
					<form action="LogonServlet?action=logon" method="post">
						<tr>
							<td colspan="2" align="center"><%=messages%></td>
						</tr>
						<tr height="30">
							<td>用户名</td>
							<td><input type"text" name="userName" style="width:200" /></td>
						</tr>
						<tr height="30">
							<td>密&nbsp;&nbsp;码</td>
							<td><input type="password" name="userPass" style="width:200" /></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" class="btn_bg" value="登陆" /> <input
								type="reset" class="btn_bg" value="重置" /> <a href="index.jsp">返回首页</a>
							</td>
						</tr>
					</form>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
