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

<title>随智阔的jsp,servlet,javabean程序</title>

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
	<table border="0" width="80%" bgcolor="E6E6FA">
		<!-- 包含头文件 -->
		<tr>
			<td colspan="20"><jsp:include page="view/FrontTop.jsp" /></td>
		</tr>
		<tr>
			<!-- 包含侧栏文件 -->
			<td valign="top"><jsp:include page="/front/view/FrontLeft.jsp"></jsp:include></td>
			<td align="center" valign="top">
				<table border="0" width="94%" style="margin-top:8">
					<tr heigth="40">
						<td>【我的文章】</td>
						<%
							List articleList = (List) request.getAttribute("articleList");
							if (articleList != null && articleList.size() != 0) {
						%>
						<td align="right"><a href="front/article/ArticleIndex.jsp">更多..</a></td>
						<%
							}
						%>
					</tr>
					<%
						//ArticleBean articleBean = (ArticleBean)articleList.get(0);
						if (articleList == null || articleList.size() == 0) {
					%>
					<tr height="100">
						<td align="center">博主还没有发表文章！</td>
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
							| 评论： <%=articleSingle.getReview()%> | 阅读：<%=articleSingle.getCount()%>
							次
						</td>
					</tr>
					<tr height="300">
						<td colspan="200"></td>
					</tr>
					<%
						i++;
							}
						}
					%>
				</table>

			</td>
		</tr>
		<!-- 包含页尾文件 -->
		<tr>
			<td colspan="2"><jsp:include page="view/FrontEnd.jsp" /></td>
		</tr>
	</table>

</body>
</html>
