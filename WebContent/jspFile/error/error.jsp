<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>出错了</title>
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>


	<body>
		<h1>出现错了了！Σ(⊙▽⊙"a")</h1>
		<table border="1">
			<tr>
				<td>错误信息</td>
				<td><%=exception.getMessage() %></td>
			</tr>
			<tr>
				<td>详情</td>
				<td><pre><kbd><%=exception.getCause() %></kbd></pre></td>
			</tr>
		</table>
		
		
		<script src="../js/jquery-1.12.4.min.js"></script>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>