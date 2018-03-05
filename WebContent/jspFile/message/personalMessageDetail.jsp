<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	//从服务器中出来的东西
	//列表
	String ttitle = "消息的标题啊啊啊";
	String tkind = "公司？！！";
	String tperson = "张三";
	String tdate = "2017年10月7日23:06:42";
	String tcontent = "我是内容我是内容我是内容我是内容我是内容我是内容"
			+"我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容";
%>
<%
	//假造数据

	request.setAttribute("pmdTitle", ttitle);//标题
	request.setAttribute("pmdKind", tkind);//分类
	request.setAttribute("pmdPerson", tperson);//发送人
	request.setAttribute("pmdDate", tdate);//时间
	request.setAttribute("pmdContent", tcontent);//内容
%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>个人消息——详情</title>
		<!-- message/personalMessageDetail.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="panel panel-primary">
			<div class="panel-heading">
				<span>消息详情</span>
				<a href="#" class="pull-right btn btn-info">返回</a>
				<!-- 到message/personalMessageList.jsp -->
				<div class="clearfix"></div>
			</div>
			<div class="panel-body">
				<h3 align="center">${pmdTitle }</h3>
				<p>
					发送分类：<span>${pmdKind }</span>
				</p>
				<p>
					发送者：<span>${pmdPerson }</span>
				</p>
				<p>
					发送时间：<span>${pmdDate }</span>
				</p>
				<span>内容：</span>
				<p class="lead">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${pmdContent }
				</p>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>