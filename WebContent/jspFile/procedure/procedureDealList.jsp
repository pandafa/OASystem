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
		<title>需处理的流程——查看列表</title>
		<!-- procedure/procedureDealList.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<!-- Default panel contents -->
				<div class="panel-heading">需处理流程</div>
				<!-- Table -->
				<table class="table table-hover table-bordered">
					<tr>
						<th>序号</th>
						<th>流程编号</th>
						<th>流程名称</th>
						<th>项目名称</th>
						<th>申请时间</th>
						<th>提交人</th>
						<th>更新时间</th>
						<th>操作</th>
					</tr>
					
					<c:forEach items="${pdlProcedureList }" var="map" varStatus="status">
					<tr>
							<td>${status.index+1+(currentPage-1)*5 }</td>
							<td>${map.get('id') }</td>
							<td>${map.get('name') }</td>
							<td>${map.get('produceName') }</td>
							<td>${map.get('createDate') }</td>
							<td>${map.get('createPerson') }</td>
							<td>${map.get('updateDate') }</td>
							<td><a href="${pageContext.request.contextPath}/procedure/needToDealDetail.do?submitId=${map.get('id') }" class="btn btn-info" type="button">处理</a></td>
							<!-- 到procedure/procedureDealEdit.jsp -->
						</tr>
					</c:forEach>
					
					
				</table>
				<!--底部开始-->
				<div class="panel-footer">

					<div class="pull-" align="center">
						<ul class="pagination">

							<li class="${currentPage==1 ? 'disabled':'' }">
								<c:if test="${currentPage!=1 }">
									<a href="${pageContext.request.contextPath}/procedure/needToDealList.do?page=${currentPage-1 }" aria-label="Previous">
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
									<a href="${pageContext.request.contextPath}/procedure/needToDealList.do?page=${pageNumber }">${pageNumber } </a>
								</li>
							</c:forEach>
							<li class="${currentPage==allPage ? 'disabled':'' }">
								<c:if test="${currentPage!=allPage }">
									<a href="${pageContext.request.contextPath}/procedure/needToDealList.do?page=${currentPage+1 }" aria-label="Previous">
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
					</div>
				</div>
				<!--底部结束-->
			</div>

		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>