<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>用户管理</title>
		<!-- manager/userManager.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>用户管理</span>
				</div>
				<div class="panel-body">
					<ul class="nav nav-tabs">
						<li role="presentation" ${umCurrentPart=='0' ? 'class="active"':'' }>
							<a href="${pageContext.request.contextPath}/manage/userManager.do">全部</a>
						</li>
						<c:forEach items="${umPartList }" var="map">
							<li role="presentation" ${map.get('partId')==umCurrentPart ? 'class="active"':'' }>
								<a href="${pageContext.request.contextPath}/manage/userManager.do?partId=${map.get('partId') }">${map.get('partName') }</a>
							</li>
						</c:forEach>
					</ul>
					<br />
					<table class="table table-hover">
						<tr>
							<th>序号</th>
							<th>工号</th>
							<th>身份证号</th>
							<th>姓名</th>
							<th>性别</th>
							<th>部门</th>
							<th>分组</th>
							<th>职务</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${umPersonList }" var="map" varStatus="status">
							<tr>
								<td>${status.index+1+(currentPage-1)*10 }</td>
								<td>${map.get('jobId') }</td>
								<td>${map.get('cardId') }</td>
								<td>${map.get('name') }</td>
								<td>${map.get('sex') }</td>
								<td>${map.get('part') }</td>
								<td>${map.get('group') }</td>
								<td>${map.get('post') }</td>
								<td>
									<a href="${pageContext.request.contextPath}/manage/userManagerEdit.do?jobId=${map.get('jobId') }">
										<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
									</a>
									<!-- 到manager/userManagerEdit.jsp -->
								</td>
							</tr>
						</c:forEach>
					</table>
					<div align="center">
						<nav aria-label="Page navigation">
							<ul class="pagination">
							<li class="${currentPage==1 ? 'disabled':'' }">
								<c:if test="${currentPage!=1 }">
									<a href="${pageContext.request.contextPath}/manage/userManager.do?partId=${umCurrentPart }&page=${currentPage-1 }" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</c:if>
								<c:if test="${currentPage==1 }">
									<a aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</c:if>
							</li>
							<c:forEach var="pageNumber" begin="1" end="${allPage }">
								<li class="${pageNumber==currentPage ? 'active':'' }">
									<a href="${pageContext.request.contextPath}/manage/userManager.do?partId=${umCurrentPart }&page=${pageNumber }">${pageNumber } </a>
								</li>
							</c:forEach>
							<li class="${currentPage==allPage ? 'disabled':'' }">
								<c:if test="${currentPage!=allPage }">
									<a href="${pageContext.request.contextPath}/manage/userManager.do?partId=${umCurrentPart }&page=${currentPage+1 }" aria-label="Previous">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</c:if>
								<c:if test="${currentPage==allPage }">
									<a aria-label="Previous">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</c:if>
							</li>
						</ul>
						</nav>
					</div>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>