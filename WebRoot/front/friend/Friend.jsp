<%@ page language="java"
	import="java.util.*,net.gicp.suizhikuo.valuebean.*" pageEncoding="UTF8"%>
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

<title>My JSP 'Friend.jsp' starting page</title>

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
	<div>
		<table border="1" width="80%" bgcolor="Olive">
			<!-- 包含头文件 -->
			<tr>
				<td colspan="20"><jsp:include page="/front/view/FrontTop.jsp" /></td>
			</tr>
			<tr>
				<!-- 包含侧栏文件 -->
				<td valign="top"><jsp:include page="/front/view/FrontLeft.jsp"></jsp:include></td>
				<td align="center" valign="top">
					<!--  //这里为内容显示区代码--> <%
 	List friendList = (List) session.getAttribute("friendList");
 	if (friendList == null || friendList.size() == 0) {
 		out.println("亲，博主还没有博客好友喔，快来告诉他你的博客");
 	} else {
 		int i = 0;
 		FriendBean friendBean;
 %>

					<table>
						<tr>
							<td align="center"><h2>
									博主有:
									<h1><%=friendList.size()%></h1>
									位好友.快把你的博客告诉他
								</h2></td>
						</tr>
					</table>


					<table width="100%" height="200" border="1">
						<th>博客名</th>
						<th>博客地址</th>
						<%
							while (i < friendList.size()) {
									friendBean = (FriendBean) friendList.get(i);
						%>



						<tr>
							<td><a href="<%=friendBean.getLink()%>"><%=friendBean.getBlog()%></a>
							</td>
							<td><a href="<%=friendBean.getLink()%>"><%=friendBean.getLink()%></a>
							</td>
						</tr>
						<%
							i++;

								}
							}
						%>
					</table> <!--  /****上面结束首页中显示文章的代码***/-->
				<td></td>
			</tr>
			<!-- 包含页尾文件 -->
			<tr>
				<td colspan="2"><jsp:include page="/front/view/FrontEnd.jsp" /></td>
			</tr>
		</table>

	</div>
</body>
</html>
