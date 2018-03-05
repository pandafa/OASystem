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
		<title>个人消息——列表</title>
		<!-- message/personalMessageList.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<p>个人消息</p>
				</div>
				<div class="panel-body">

					<table class="table table-hover">
						<tr>
							<th>序号</th>
							<th>状态</th>
							<th>标题</th>
							<th>来源</th>
							<th>发送者</th>
							<th>时间</th>
						</tr>
						<c:forEach items="${pmlMsgList }" var="map" varStatus="status">
							<tr>
								<td>${status.index+1+(currentPage-1)*10 }</td>
								<td>
									<c:choose>
										<c:when test="${map.get('status')==0 }">
											<span class="label label-success">未读</span>
										</c:when>
										<c:otherwise>
											<span class="label label-default">已读</span>
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<a href="${pageContext.request.contextPath}/message/lookMessage.do?msgId=${map.get('id') }">${map.get('title') }</a>
								</td>
								<td>${map.get('source') }</td>
								<td>${map.get('sendPerson') }</td>
								<td>${map.get('date') }</td>
							</tr>
						</c:forEach>
					</table>

				</div>
				<div class="panel-footer" align="center">
					<nav aria-label="Page navigation">
						<ul class="pagination">

							<li class="${currentPage==1 ? 'disabled':'' }">
								<c:if test="${currentPage!=1 }">
									<a href="#pageNumber=${currentPage-1 }" aria-label="Previous">
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
									<a href="#pageNumber=${pageNumber }">${pageNumber } </a>
								</li>
							</c:forEach>
							<li class="${currentPage==allPage ? 'disabled':'' }">
								<c:if test="${currentPage!=allPage }">
									<a href="#pageNumber=${currentPage+1 }" aria-label="Previous">
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

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>