<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>群组管理</title>
		<!-- manager/groupManager.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>群组管理</span>
				</div>
				<div class="panel-body">
					<div>
						<c:if test="${gmCanPart==1 }">
							<div align="center">
								<a class="btn btn-success btn-lg" href="${pageContext.request.contextPath}/manage/groupManagerPart.do" role="button">部门管理</a>
								<!-- 转到manager/groupManagerPart.jsp -->
							</div>
							<br />
						</c:if>
						<c:if test="${gmCanGroup==1 }">
							<div align="center">
								<a class="btn btn-warning btn-lg" href="${pageContext.request.contextPath}/manage/groupManagerGroup.do" role="button">小组管理</a>
								<!-- 转到manager/groupManagerGroup.jsp -->
							</div>
							<br />
						</c:if>
						<c:if test="${gmCanMember==1 }">
							<div align="center">
								<a class="btn btn-info btn-lg" href="${pageContext.request.contextPath}/manage/groupManagerMember.do" role="button">成员管理</a>
								<!-- 转到manager/groupManagerMember.jsp -->
							</div>
							<br />
						</c:if>
					</div>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>