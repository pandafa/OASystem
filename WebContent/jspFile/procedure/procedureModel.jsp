<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>流程模板</title>
		<!-- procedure/procedureModel.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<!-- Default panel contents -->
				<div class="panel-heading">流程模板</div>
				<div class="panel-body">
					<p class="lead">这里是流程模板。在这里可以对流程模板进行查看、创建、修改和删除。
					“流程名称”即流程的主名字。
					“流程简介”阐述本流程的介绍。
					“填写说明”会在提交申请的时候显示。
					“备注”会在查看提交的流程中最下方的备注显示。
					“表格题头”会在查看提交的流程的详细中的题头显示。
					“项目名称”会在提交后的列表中的“项目名称”一栏中显示，即流程的副名字。
					“填选项”是自定义需要填写的内容。
					“审批过程”是对过程的审批流程，有顺序的。
					</p>
				</div>

				<!-- Table -->
				<table class="table table-hover table-bordered">
					<tr>
						<th>序号</th>
						<th>流程编号</th>
						<th>名称</th>
						<th>简介</th>
						<th>创建时间</th>
						<th>创建人</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${pmTableList }" var="map" varStatus="status">
						<tr>
							<td>${status.index+1 }</td>
							<td>${map.get("id") }</td>
							<td>${map.get("name") }</td>
							<td>${map.get("introduction") }</td>
							<td>${map.get("createDate") }</td>
							<td>${map.get("createPerson") }</td>
							<td>
								<a href="${pageContext.request.contextPath}/procedure/modelLook.do?modelId=${map.get('id') }" class="btn btn-info">查看</a>
								<!-- 初步到procedure/procedureModelEdit.jsp ，不可编辑 -->
								<c:if test="${map.get('canChange')=='1' }">
									<a href="${pageContext.request.contextPath}/procedure/modelEdit.do?modelId=${map.get('id') }" class="btn btn-warning">修改</a>
									<!-- 转到procedure/procedureModelEdit.jsp -->
								</c:if>
								<c:if test="${map.get('canDel')=='1' }">
									<a href="${pageContext.request.contextPath}/procedure/delModel.do?modelId=${map.get('id') }" class="btn btn-danger">删除</a>
									<!-- 到删除指定 -->
								</c:if>
								
							</td>
						</tr>
					</c:forEach>
				</table>
				<!--底部开始-->
				<div class="panel-footer">
					<div class="pull-left">
						<c:if test="${pmCanAdd==true }">
							<a class="btn btn-success" href="${pageContext.request.contextPath}/procedure/modelEdit.do">添加</a>
							<!-- 添加，到procedure/procedureModelEdit.jsp -->
						</c:if>
					</div>
					<div class="pull-right">
						<ul class="pagination">

							<li class="${currentPage==1 ? 'disabled':'' }">
								<c:if test="${currentPage!=1 }">
									<a href="${pageContext.request.contextPath}/procedure/modelList.do?page=${currentPage-1 }" aria-label="Previous">
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
									<a href="${pageContext.request.contextPath}/procedure/modelList.do?page=${pageNumber }">${pageNumber } </a>
								</li>
							</c:forEach>
							<li class="${currentPage==allPage ? 'disabled':'' }">
								<c:if test="${currentPage!=allPage }">
									<a href="${pageContext.request.contextPath}/procedure/modelList.do?page=${currentPage+1 }" aria-label="Previous">
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
					<div class="clearfix"></div>
				</div>
				<!--底部结束-->
			</div>

		</div>
		
		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>