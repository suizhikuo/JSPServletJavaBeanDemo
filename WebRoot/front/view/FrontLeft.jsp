<%@ page language="java" import="java.util.*,net.gicp.suizhikuo.valuebean.*" pageEncoding="UTF8"%>
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

<title>My JSP 'FrontLeft.jsp' starting page</title>

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
<body bgcolor="aabbaa">
	<table border="1" width="60%" rules="all" cellpadding="0"
		cellspacing="0" bgcolor="ccbbcc">
		<!-- 首页留言展示 -->
		<%
			List iWordList = (List) session.getAttribute("iWordList");
			if (iWordList == null) {
				out.println("还没有留言喔，快来做沙发！");
			} else {
		%>
		<tr height="0" align="center">
			<td bgcolor="aa6600">
				<h3>最新留言</h3>
			</td>
		</tr>
		<%
			int i = 0;
				while (i < iWordList.size()) {
					WordBean wordSingle = (WordBean) iWordList.get(i);
		%>
		<tr>
			<td style="text-indent:20"><b>【<%=wordSingle.getAuthor()%>】:
			</b></td>
		</tr>
		<tr align="left"></tr>
		<tr>
			<td colspan="2" valign="top"><%=wordSingle.getContent()%></td>
		</tr>
		<tr height="1">
			<td colspan="2"></td>
		</tr>
		<%
			i++;
				}
		%>
		<%
			}
		%>
		<br />
		<!-- 首页博主推荐展示 -->
		<%
			/*
									List iFriendList = (List) session.getAttribute("iFriendList");
									if (iFriendList == null || iFriendList.size() == 0) {
										out.println("博主还没有好友，快来告诉他你的博客");
									} else {
										int i = 0;							
										FriendBean friendBean;
			 */
		%>

		<tr>
			<td bgcolor="aa6600" align="center"><h3>博主推介</h3></td>
		</tr>
		<%
			//	while (i < iFriendList.size()) {								
			//			friendBean = (FriendBean) iFriendList.get(i);
		%>
		<tr>
			<td></td>
		</tr>
		<%
			/*
			 i++;								
			 }
			 }
			 */
		%>
		<!-- 首页博客好友展示 -->
		<%
			List iFriendList = (List) session.getAttribute("iFriendList");
			if (iFriendList == null || iFriendList.size() == 0) {
				out.println("还没有好友，快把你的博客放到碗里来");
			} else {
				int i = 0;
				FriendBean friendBean;
		%>
		<tr>
			<td bgcolor="aa6600" align="center"><h3>推荐博客</h3></td>
		</tr>
		<%
			while (i < iFriendList.size()) {
					friendBean = (FriendBean) iFriendList.get(i);
		%>
		<tr>
			<td><a href="<%=friendBean.getLink()%>"><%=friendBean.getBlog()%></a>
			</td>
		</tr>
		<%
			i++;
				}
			}
		%>

	</table>
</body>

</body>

</html>
