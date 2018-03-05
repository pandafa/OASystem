<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 主要框架 --%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>${myPageTitle }</title>
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<script type="text/javascript">
		function logoutPrompt(){
			var res = confirm('是否要注销？');
			if(res!=1){
				event.preventDefault();
			}
		}
	</script>

	<body>
		
		<div class="container">
			<div></div>
		</div>
		<div class="container">
			<!--导航条开始-->
			<nav class="navbar navbar-default">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
						<span class="sr-only"></span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
						<a class="navbar-brand" href="#">0A管理系统</a>
					</div>
					<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav navbar-right">
							<li>
								<p class="navbar-text"><span>${sessionScope.userKindName }</span>，<span>${sessionScope.userName }</span>，您好！</p>
							</li>
							<li>
								<a href="#">
									<span class="glyphicon glyphicon-bell" aria-hidden="true"></span>
									<span class="badge">${sessionScope.myPageMessagePrompt>0 ? sessionScope.myPageMessagePrompt:'' }</span>
								</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/logout.do" onclick="logoutPrompt()">注销</a>
							</li>
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
					<div class="list-group">
						<!-- 流程开始 -->
						<a href="#" class="list-group-item active">流程审批</a>
						<a href="${pageContext.request.contextPath}/procedure/submitList.do" class="list-group-item list-group-item-${ myPageNav=='1' ? 'success':'' } btn ">流程提交</a>
						<a href="${pageContext.request.contextPath}/procedure/myList.do" class="list-group-item list-group-item-${ myPageNav=='2' ? 'success':'' } btn ">我的流程</a>
						<a href="${pageContext.request.contextPath}/procedure/modelList.do" class="list-group-item list-group-item-${ myPageNav=='3' ? 'success':'' } btn ">流程模板</a>
						<a href="${pageContext.request.contextPath}/procedure/needToDealList.do" class="list-group-item list-group-item-${ myPageNav=='4' ? 'success':'' } btn ">需处理流程<span class="badge">${sessionScope.myPageNeedDeal>0 ? sessionScope.myPageNeedDeal:'' }</span></a>
						<!--流程结束-->
						<!--文件开始-->
						<a href="#" class="list-group-item active">文件中心</a>
						<a href="${pageContext.request.contextPath}/file/fileHome.do" class="list-group-item list-group-item-${ myPageNav=='5' ? 'success':'' } btn ">文件仓库</a>
						<!--文件结束-->
						<!--消息开始-->
						<a href="#" class="list-group-item active">消息中心</a>
						<a href="${pageContext.request.contextPath}/message/messagePlaza.do" class="list-group-item list-group-item-${ myPageNav=='6' ? 'success':'' } btn ">消息广场<span class="badge">${sessionScope.myPageNotice>0 ? sessionScope.myPageNotice:'' }</span></a>
						<a href="${pageContext.request.contextPath}/message/personMessageList.do" class="list-group-item list-group-item-${ myPageNav=='7' ? 'success':'' } btn ">个人消息<span class="badge">${sessionScope.myPageMessage>0 ? sessionScope.myPageMessage:'' }</span></a>
						<a href="${pageContext.request.contextPath}/message/sendMessage.do" class="list-group-item list-group-item-${ myPageNav=='8' ? 'success':'' } btn ">发送消息</a>
						<a href="${pageContext.request.contextPath}/message/sendNotice.do" class="list-group-item list-group-item-${ myPageNav=='9' ? 'success':'' } btn ">发布公告</a>
						<!--消息结束-->
						<!--用户中心开始-->
						<a href="#" class="list-group-item active">用户中心</a>
						<a href="${pageContext.request.contextPath}/changePassword.do" class="list-group-item list-group-item-${ myPageNav=='10' ? 'success':'' } btn ">密码修改</a>
						<a href="${pageContext.request.contextPath}/personInfo.do" class="list-group-item list-group-item-${ myPageNav=='11' ? 'success':'' } btn ">个人信息</a>
						<!--用户中心结束-->
						<!--管理中心开始-->
						<a href="#" class="list-group-item active">管理中心</a>
						<a href="${pageContext.request.contextPath}/manage/userManager.do" class="list-group-item list-group-item-${ myPageNav=='12' ? 'success':'' } btn ">
							用户管理
						</a>
						<a href="${pageContext.request.contextPath}/manage/manageGroup.do" class="list-group-item list-group-item-${ myPageNav=='13' ? 'success':'' } btn ">群组管理</a>
						<a href="${pageContext.request.contextPath}/manage/addUser.do" class="list-group-item list-group-item-${ myPageNav=='14' ? 'success':'' } btn ">添加新用户</a>
						<!--管理中心结束-->
					</div>
				</div>
				<div class="col-md-9">
					<jsp:include page="${myPageUrlName }"></jsp:include>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>