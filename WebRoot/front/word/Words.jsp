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

<title>My JSP 'Words.jsp' starting page</title>

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
 	List wordList = (List) session.getAttribute("wordList");
 	String wordSum;
 	Object obj = session.getAttribute("wordSum");
 	if (obj != null)
 		wordSum = obj.toString();
 	else
 		wordSum = "0";
 	if (wordList == null) {
 		out.println("还没有留言喔，快来做沙发！");
 	} else {
 %>
					<table border="0" width="100%" rules="all" cellpadding="0"
						cellspacing="0">
						<tr height="55" align="right">
							<td>当前留言有<%=wordSum%>&nbsp;条留言;
							</td>
						</tr>
						<tr height="15">
							<td></td>
						</tr>
						<!-- 以下是显示文章评论代码： -->
						<%
							int i = 0;
								while (i < wordList.size()) {
									WordBean wordSingle = (WordBean) wordList.get(i);
						%>
						<tr>
							<td style="text-indent:20">【<%=wordSingle.getId()%>】<b><%=wordSingle.getAuthor()%></b></td>
						</tr>
						<tr align="right">
							<td><%=wordSingle.getSdTime()%> &nbsp;&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2" valign="top"><%=wordSingle.getContent()%></td>
						</tr>
						<tr height="1">
							<td background="image/line.jpg" colspan="2"></td>
						</tr>
						<%
							i++;
								}
								out.println("</table>");
							}

							for (int i = 0; i < (Integer.parseInt(wordSum) + 9) / 10; i++) {
						%>
						<a
							href="WordServlet?action=readWord&wordBegin=<%=i + 1%>&count=<%=10%>"><%=i + 1%></a>
						<%
							if (i != 0 && i % 10 == 0)
									out.println(" <br/>");
						%>

						<%
							}
						%>


						<!--  以下是填写评论内容的代码： -->

						<p>
							<b>我要吐槽 </b>
						</p>

						<form action="WordServlet" method="post">
							<input type="hidden" name="action" value="addWord">
							<table width="95%" border="0" cellspacing="8" cellpadding="0">
								<tr>
									<td width="80" align="center">俺的名字</td>
									<td><input type="text" name="author" size="30" value=""></td>
								</tr>
								<tr>
									<td align="center">吐槽内容</td>
									<td><textarea name="content" rows="10" cols="50"></textarea></td>
								</tr>
								<tr>
									<td></td>
									<td><input type="submit" value="提交" style="width:50" /> <input
										type="reset" value="重置" style="width:50" /></td>
								</tr>
							</table>

						</form>

						<td></td>
						</tr>
						<!-- 包含页尾文件 -->
						<tr>
							<td colspan="2"><jsp:include page="../view/FrontEnd.jsp" /></td>
						</tr>
					</table>

					</div>
</body>
</html>
