<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>${pmdIsNotice==1 ? '公告':'消息' }详情</title>
		<!-- message/messagePlazaDetail.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="panel panel-primary">
			<div class="panel-heading">
				<span>${pmdIsNotice==1 ? '公告':'消息' }详情</span>
				<a href="${pageContext.request.contextPath}/${pmdPreUrl }" class="pull-right btn btn-info">返回</a>
				<!-- 到上一级 -->
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