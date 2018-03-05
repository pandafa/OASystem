<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 查看 -->
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>主框架</title>
<link
	href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<div>123</div>
	</div>
	<div class="container">
		<!--导航条开始-->
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
						aria-expanded="false">
						<span class="sr-only"></span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">XXX 0A管理系统</a>
				</div>
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav navbar-right">
						<li>
							<a href="#">
								<span class="glyphicon glyphicon-bell" aria-hidden="true"></span>
								<span class="badge">4</span>
							</a>
						</li>
						<li><a href="#">注销</a></li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>
		<!--导航条结束-->
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-3">
			<ul class="list-group">
			  <!-- 流程开始 -->
			  <li class="list-group-item active">流程审批</li>
			  <li class="list-group-item list-group-item- btn ">流程提交</li>
			  <li class="list-group-item list-group-item- btn ">我的流程</li>
			  <li class="list-group-item list-group-item- btn ">流程模板</li>
			  <li class="list-group-item list-group-item- btn ">需处理流程<span class="badge">4</span></li>
			  <!--流程结束-->
			  <!--文件开始-->
			  <li class="list-group-item active">文件中心</li>
			  <li class="list-group-item list-group-item- btn ">文件仓库</li>
			  <!--文件结束-->
			  <!--消息开始-->
			  <li class="list-group-item active">消息中心</li>
			  <li class="list-group-item list-group-item- btn ">消息广场<span class="badge">4</span></li>
			  <li class="list-group-item list-group-item- btn ">个人消息</li>
			  <li class="list-group-item list-group-item- btn ">发送消息</li>
			  <li class="list-group-item list-group-item- btn ">发布公告</li>
			  <!--消息结束-->
			  <!--用户中心开始-->
			  <li class="list-group-item active">用户中心</li>
			  <li class="list-group-item list-group-item- btn ">密码修改</li>
			  <li class="list-group-item list-group-item- btn ">个人信息</li>
			  <!--用户中心结束-->
			  <!--管理中心开始-->
			  <li class="list-group-item active">管理中心</li>
			  <li class="list-group-item list-group-item- btn ">用户管理</li>
			  <li class="list-group-item list-group-item- btn ">群组管理</li>
			  <li class="list-group-item list-group-item- btn ">添加新用户</li>
			  <!--管理中心结束-->
			</ul>
		</div>
			<div class="col-md-9">
			
				<jsp:include page="<%= %>"></jsp:include>  
				<%-- <%@ include file="procedure/procedureDealEdit.jsp"%> --%><!-- ok -->
				<%@ include file="../message/sendNotice.jsp"%> 
				<%-- <%@ include file="user/persionInfo.jsp"%> --%>
				<%-- <%@ include file="user/changePassword.jsp"%> --%>
				<%-- <%@ include file="manager/addUsers.jsp"%> --%>
				<%-- <%@ include file="manager/groupManager.jsp"%> --%>
				<%-- <%@ include file="manager/groupManagerPart.jsp"%> --%>
				<%-- <%@ include file="manager/groupManagerGroup.jsp"%> --%>
				<%-- <%@ include file="manager/groupManagerMember.jsp"%> --%>
				<%-- <%@ include file="manager/userManager.jsp"%> --%>
				<%-- <%@ include file="manager/userManagerEdit.jsp"%> --%>
				 <%-- <%@ include file="file/fileHome.jsp"%>  --%>
				<%-- <%@ include file="forgetPassword.jsp"%>
				<%@ include file="manager/groupManagerPart.jsp"%> --%>
			</div>
		</div>
	</div>


	<script
		src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>