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
		<title>我的流程——详细</title>
		<!-- procedure/myProcedureDetail.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<!-- Default panel contents -->
				<div class="panel-heading">
					<span>我的流程——查看</span>
					<a href="${pageContext.request.contextPath}${mpdBackUrl }" class="btn btn-success pull-right">返回</a>
					<a href="${pageContext.request.contextPath}/procedure/downFileOfWord.do?submitId=${mpdId}" class="btn btn-info pull-right">下载为word文档</a>
					
					<!-- 返回到procedure/myProcedureList.jsp -->
					<div class="clearfix"></div>
				</div>
				<div class="panel-body">
					<div>
						<span>状态：</span>
						<c:choose>
							<c:when test="${mpdStatus==1 }">
								<span class="label label-success">通过</span>
							</c:when>
							<c:when test="${mpdStatus==2 }">
								<span class="label label-default">未通过</span>
							</c:when>
							<c:when test="${mpdStatus==3 }">
								<span class="label label-warning">进行中</span>
							</c:when>
							<c:when test="${mpdStatus==4 }">
								<span class="label label-danger">错误</span>
							</c:when>
							<c:otherwise>
								<span class="label label-info">异常</span>
							</c:otherwise>
						</c:choose>
					</div>
					<div>
						<h1 align="center">${mpdTname }表</h1>
						<p align="right">编号：${mpdIdStr }</p>
						<table class="table table-bordered">
							<tr>
								<td>${mpdProduceName }</td>
								<td colspan="3">
									<p>${mpdProduceNameTitle }</p>
								</td>
							</tr>
							<tr>
								<td>提交人</td>
								<td>
									<p>${mpdCreatePerson }</p>
								</td>
								<td>申请时间</td>
								<td>
									<p>${mpdCreateDate }</p>
								</td>
							</tr>
							<tr>
								<td>提交人所属部门</td>
								<td>
									<p>${mpdPart }</p>
								</td>
								<td>提交人所属小组</td>
								<td>
									<p>${mpdGroup }</p>
								</td>
							</tr>
							<c:forEach var="mapIndex" varStatus="status" begin="1" end="${mpdBlankMap.size() }">
								<c:choose>
									<c:when test="${mapIndex%2!=0 }">
										<c:if test="${mapIndex!=mpdBlankMap.size() }">
											<tr>
												<td>${mpdBlankMap.get(status.index).get('title') }</td>
												<td>
													<p>${mpdBlankMap.get(status.index).get('content') }</p>
												</td>
										</c:if>
										<c:if test="${mapIndex==mpdBlankMap.size() }">
											<tr>
												<td>${mpdBlankMap.get(status.index).get("title") }</td>
												<td>
													<p>${mpdBlankMap.get(status.index).get('content') }</p>
												</td>
											</tr>
										</c:if>
									</c:when>
									<c:otherwise>
											<td>${mpdBlankMap.get(status.index).get("title") }</td>
											<td>
												<p>${mpdBlankMap.get(status.index).get('content') }</p>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:forEach varStatus="status" begin="1" end="${mpdProcedureMap.size() }">
								<c:choose>
									<c:when test="${mpdProcedureMap.get(status.index).get('isWorked')=='ok' }">
										<!-- 审批完 -->
										<tr>
											<td>${mpdProcedureMap.get(status.index).get('title') }</td>
											<td colspan="3">
												<p>意见：${mpdProcedureMap.get(status.index).get('content') }</p>
												<p align="right">姓名：${mpdProcedureMap.get(status.index).get('name') }</p>
												<p align="right">时间：${mpdProcedureMap.get(status.index).get('time') }</p>
											</td>
										</tr>
									</c:when>
									<c:when test="${mpdProcedureMap.get(status.index).get('isWorked')=='pass' }">
										<!-- 不审批 -->
										<tr>
											<td>${mpdProcedureMap.get(status.index).get('title') }</td>
											<td colspan="3">
												<p>意见：</p>
												<p align="right">姓名：</p>
												<p align="right">时间：&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日</p>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<!-- 未审批 -->
										<tr>
											<td>${mpdProcedureMap.get(status.index).get('title') }</td>
											<td colspan="3">
												<p>意见：（待审批）</p>
												<p align="right">姓名：</p>
												<p align="right">时间：&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日</p>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</table>
						<c:if test="${mpdShowFileName!=null }">
							<div>
								<span>附件：</span>
								<a href="${pageContext.request.contextPath}/procedure/downFile.do?realFile=${mpdFileName }&showFile=${mpdShowFileName }">${mpdShowFileName }</a>
								<!-- 到特定的下载位置 -->
							</div>
						</c:if>
						<div>
							<span>备注：</span>
							<p>${mpdRemark }</p>
						</div>
					</div>
				</div>
			</div>

		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>