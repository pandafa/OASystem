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
		<title>流程提交——选择列表</title>
		<!-- procedure/procedureSubmitList.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<!-- Default panel contents -->
				<div class="panel-heading">流程提交——选择模板</div>
				<div class="panel-body">
					<p class="lead">这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。这里是描述字符。</p>
				</div>
				
				<!-- Table -->
				<table class="table table-hover table-bordered">
					<tr>
						<th>序号</th>
						<th>名称</th>
						<th>简介</th>
						<th>创建时间</th>
						<th>创建人</th>
						<th>操作</th>
					</tr>
					<c:forEach var="myList" items="${procedureModelList }" varStatus="status" >
						<tr>
							<td>${status.index+1+(currentPage-1)*5 }</td>
							<td>${myList.get("name") }</td>
							<td>${myList.get("introduction") }</td>
							<td>${myList.get("createDate") }</td>
							<td>${myList.get("createPerson") }</td>
							<td><a href="${pageContext.request.contextPath}/procedure/submitDetail.do?modelId=${myList.get('id') }" class="btn btn-info" type="button">选择</a></td>
							<!-- 选择后到procedure/procedureSubmitEdit.jsp -->
						</tr>
					</c:forEach>
				</table>
				<!--底部开始-->
				<div class="panel-footer">

					<div class="pull-" align="center">
						<ul class="pagination">

							<li class="${currentPage==1 ? 'disabled':'' }">
								<c:if test="${currentPage!=1 }">
									<a href="${pageContext.request.contextPath}/procedure/submitList.do?page=${currentPage-1 }" aria-label="Previous">
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
									<a href="${pageContext.request.contextPath}/procedure/submitList.do?page=${pageNumber }">${pageNumber } </a>
								</li>
							</c:forEach>
							<li class="${currentPage==allPage ? 'disabled':'' }">
								<c:if test="${currentPage!=allPage }">
									<a href="${pageContext.request.contextPath}/procedure/submitList.do?page=${currentPage+1 }" aria-label="Previous">
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