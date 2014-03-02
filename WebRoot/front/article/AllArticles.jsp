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

<title>My JSP 'AllArticles' starting page</title>

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
	<table border="0" width="80%" bgcolor="Olive">
		<!-- 包含头文件 -->
		<tr>
			<td colspan="20"><jsp:include page="/front/view/FrontTop.jsp" /></td>
		</tr>
		<tr>
			<!-- 包含侧栏文件 -->
			<td valign="top"><jsp:include page="/front/view/FrontLeft.jsp"></jsp:include></td>
			<td align="center" valign="top">
				<table border="0" width="94%" style="margin-top:8">
					<tr heigth="40">
						<td>【我的文章】</td>


					</tr>
					<%
						List articleList = (List) session.getAttribute("articleList");
						String articleSumStr = session.getAttribute("articleSum")
								.toString();
						int articleSum;
						if (articleSumStr == null)
							articleSum = 0;
						else
							articleSum = Integer.parseInt(articleSumStr);
						if (articleList == null || articleList.size() == 0) {
					%>
					<tr height="100">
						<td align="center"><li>博主还没有发表文章！</li></td>
					</tr>
					<%
						} else {
							int i = 0;
							while (i < articleList.size()) {
								ArticleBean articleSingle = (ArticleBean) articleList
										.get(i);
					%>
					<tr>
						<td class="tdg" style="text-ident:20" colspan="2">【】<a
							href="ArticleServlet?action=read&id=<%=articleSingle.getId()%>">

								<b><%=articleSingle.getTitle()%> </b>
						</a> [<%=articleSingle.getCreate()%>]
						</td>
					</tr>
					<tr>
						<td colspan="2" valign="top"><%=articleSingle.getContent()%></td>
					</tr>
					<tr>
						<td colspan="2"><a
							href="ArticleServlet?action=read&id=<%=articleSingle.getId()%>">阅读全文</a>

						</td>
					</tr>
					<tr>
						<td align="right" colspan="2">发表时间：<%=articleSingle.getSdTime()%>
							| 评论： <%=session.getAttribute("reviewSum")%> | 阅读：<%=articleSingle.getCount()%>
							次
						</td>
					</tr>
					<tr height="300">
						<td background="../images/cs.jpg" colspan="200"></td>
					</tr>
					<%
						i++;
							}
						}
					%>
				</table> <%
 	for (int i = 0; i < (articleSum + 9) / 10; i++) {
 %> <a
				href="ArticleServlet?action=all&articleBegin=<%=i + 1%>&articlecount=<%=10%>"><%=i + 1%></a>
				<%
					if (i != 0 && i % 10 == 0)
							out.println(" <br/>");
				%> <%
 	}
 %>
			</td>
		</tr>
		<!-- 包含页尾文件 -->
		<tr>
			<td colspan="2"><jsp:include page="/front/view/FrontEnd.jsp" /></td>
		</tr>
	</table>
</body>
</html>
