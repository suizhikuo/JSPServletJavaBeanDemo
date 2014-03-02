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

<title>My JSP 'ArticleSingle.jsp' starting page</title>

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
 	ArticleBean single = (ArticleBean) session
 			.getAttribute("readSingle");
 	String reviewSumStr = session.getAttribute("reviewSum").toString();
 	int reviewSum = 0;
 	if (reviewSumStr != null) {
 		reviewSum = Integer.parseInt(reviewSumStr);
 	}
 	if (single == null) {
 		out.println("阅读文章失败");
 		System.out.println("阅读文章失败");
 	} else {
 %>
					<table border="0" width="100%" rules="all" cellpadding="0"
						cellspacing="0">
						<tr height="55" align="right">
							<td background="images/cs.jpg">发表时间：<%=single.getSdTime()%>&nbsp;&nbsp;&nbsp;&nbsp;
								评论：<%=reviewSum%> 条&nbsp;&nbsp;&nbsp;&nbsp; 阅读：<%=single.getCount()%>
								次&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr height="15">
							<td></td>
						</tr>
						<tr>
							<td align="center"><b><font style="font-size:25px"><%=single.getTitle()%></font></b>
							</td>
						</tr>
						<tr>
							<td><%=single.getCreate()%>:<%=single.getInfo()%></td>
						</tr>
						<tr>
							<td valign="top"><%=single.getContent()%>
						</tr>
						</td>
					</table> <!--<a href="javascript:window.history.go(-1)">返回</a> -->
					<hr width="95%" style="margin-top:5" size="1"> <!--	 以下是显示文章评论代码：-->

					<%
						List reviewList = (ArrayList) session
									.getAttribute("reviewList");
							if (reviewList == null || reviewList.size() == 0) {
								out.println("<p><li>该文章目前没有评论</li>");
							} else {

								out.println("<table border='0' width='100%'> ");
								out.println("<tr><td align='center'>【文章评论共" + reviewSum
										+ "条】</td></tr>");
								int i = 0;
								while (i < reviewList.size()) {
									ReviewBean reviewSingle = (ReviewBean) reviewList
											.get(i);
					%>
				
			<tr>
				<td style="text-indent:20">【】<b><%=reviewSingle.getAuthor()%>
						<%
							System.out.println("test***********author"
												+ reviewSingle.getAuthor());
						%></b></td>
			</tr>
			<tr align="right">
				<td><%=reviewSingle.getSdTime()%> &nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" valign="top"><%=reviewSingle.getContent()%>
			</tr>
			</td>
			<tr height="1">
				<td colspan="2"></td>
			</tr>
			<%
				i++;
						}
						out.println("</table>");
					}
					for (int i = 0; i < (reviewSum + 9) / 10; i++) {
			%>
			<a
				href="ArticleServlet?action=read&reviewBegin=<%=i + 1%>&reviewCount=10&id=<%=single.getId()%>"><%=i + 1%></a>
			<%
				if (i != 0 && i % 10 == 0)
							out.println(" <br/>");
			%>
			<%
				}
			%>
			<!--  以下是填写评论内容的代码： -->
			<p>
				<b>发表评论 </b>
			</p>

			<form action="ArticleServlet" method="post">
				<input type="hidden" name="action" value="followAdd"> <input
					type="hidden" name="articleId" value="<%=single.getId()%>">
				<table width="95%" border="0" cellspacing="8" cellpadding="0">
					<tr>
						<td width="80" align="center">俺的名字</td>
						<td><input type="text" name="author" size="30" value="匿名好基友"></td>
					</tr>
					<tr>
						<td align="center">评论内容</td>
						<td><textarea name="content" row="10" cols="50"></textarea></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="提交" style="width:50" /> <input
							type="reset" value="重置" style="width:50" /></td>
					</tr>
				</table>

			</form>

			<!--  /****上面结束首页中显示文章的代码***/-->
			<td></td>
			</tr>
			<!-- 包含页尾文件 -->
			<tr>
				<td colspan="2"><jsp:include page="../view/FrontEnd.jsp" /></td>
			</tr>
		</table>
		<%
			}
		%>

	</div>
</body>
</html>
