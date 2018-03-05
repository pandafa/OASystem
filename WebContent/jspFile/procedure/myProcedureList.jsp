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
		<title>我的流程——列表</title>
		<!-- procedure/myProcedureList.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<!-- Default panel contents -->
				<div class="panel-heading">我的流程</div>
				<!-- Table -->
				<table class="table table-hover table-bordered">
					<tr>
						<th>序号</th>
						<th>流程编号</th>
						<th>流程名称</th>
						<th>项目名称</th>
						<th>创建时间</th>
						<th>创建人</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${mplProcedureList }" var="map" varStatus="status">
						<tr>
							<td>${status.index+1+(currentPage-1)*5 }</td>
							<td>${map.get('id') }</td>
							<td>${map.get('name') }</td>
							<td>${map.get('produceName') }</td>
							<td>${map.get('createDate') }</td>
							<td>${map.get('createPerson') }</td>
							<td>
								<c:choose>
									<c:when test="${map.get('status')==1 }">
										<span class="label label-success">通过</span>
									</c:when>
									<c:when test="${map.get('status')==2 }">
										<span class="label label-default">未通过</span>
									</c:when>
									<c:when test="${map.get('status')==3 }">
										<span class="label label-warning">进行中</span>
									</c:when>
									<c:when test="${map.get('status')==4 }">
										<span class="label label-danger">错误</span>
									</c:when>
									<c:otherwise>
										<span class="label label-info">异常</span>
									</c:otherwise>
								</c:choose>
							</td>
							<td><a href="${pageContext.request.contextPath}/procedure/mySubmitDetail.do?submitId=${map.get('id') }" class="btn btn-info" type="button">查看</a></td>
							<!-- 查看后，到procedure/myProcedureDetail.jsp -->
						</tr>
					</c:forEach>
					
				</table>
				<!--底部开始-->
				<div class="panel-footer">

					<div class="pull-" align="center">
						<ul class="pagination">
							<li class="${currentPage==1 ? 'disabled':'' }">
								<c:if test="${currentPage!=1 }">
									<a href="${pageContext.request.contextPath}/procedure/myList.do?page=${currentPage-1 }" aria-label="Previous">
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
									<a href="${pageContext.request.contextPath}/procedure/myList.do?page=${pageNumber }">${pageNumber } </a>
								</li>
							</c:forEach>
							<li class="${currentPage==allPage ? 'disabled':'' }">
								<c:if test="${currentPage!=allPage }">
									<a href="${pageContext.request.contextPath}/procedure/myList.do?page=${currentPage+1 }" aria-label="Previous">
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