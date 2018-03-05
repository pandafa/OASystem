<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>忘记密码</title>
		<!-- forgetPassword.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath}/jspFile/js/md5.js"></script>
		<script type="text/javascript">
	        
	        //表单验证
	        function checkFrom(){
	            var jobId = document.getElementById("jobId");
	            if(!jobId.value){
	                alert("请填写工号！");
	                event.preventDefault();
	                return;
	            }
	            var cardId = document.getElementById("cardId");
	            if(!cardId.value){
	                alert("请填写身份证号！");
	                event.preventDefault();
	                return;
	            }
	            var username = document.getElementById("username");
	            if(!username.value){
	                alert("请填写您的姓名！");
	                event.preventDefault();
	                return;
	            }
	            var newPassword1 = document.getElementById("newPassword1").value;
	            if(!newPassword1){
	                alert("请填写新密码！");
	                event.preventDefault();
	                return;
	            }
	            var newPassword2 = document.getElementById("newPassword2").value;
	            if(!newPassword2){
	                alert("请再次填写新密码！");
	                event.preventDefault();
	                return;
	            }
	            if(newPassword2 != newPassword1){
	                alert("两次密码输入不同，请重新输入");
	                event.preventDefault();
	                return;
	            }
	            document.getElementById("newPassword1").value = md5Password(newPassword1);
	            document.getElementById("newPassword2").value = md5Password(newPassword2);
	        }
	        
	      	//加载完成
	        window.addEventListener("load",function(){
	            //表单提交验证
	            var myFromForgetPassword = document.getElementById("myFromForgetPassword");
	            if(myFromForgetPassword.addEventListener){
	            	myFromForgetPassword.addEventListener("submit",function(){
	            		checkFrom();
	                });
	            }else{
	            	myFromForgetPassword.attachEvent("onsubmit",function(){
	            		checkFrom();
	                });
	            }
	        });
	    </script>
	</head>

	<body class="bg-info">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<div class="row">
						<form action="${pageContext.request.contextPath}/forgetPasswordForm.do" id="myFromForgetPassword" method="post" class="form-horizontal">
							<h1 class="center-block" align="center">找回密码</h1>
							<br />
							<br />
							<br />
							<!-- 工号 -->
							<div class="form-group">
								<label for="jobId" class="col-sm-3 control-label">工号</label>
								<div class="col-sm-9">
									<input type="text" name="jobId" class="form-control" id="jobId" placeholder="工号（必填）">
								</div>
							</div>
							<!-- 身份证号 -->
							<div class="form-group">
								<label for="cardId" class="col-sm-3 control-label">身份证号</label>
								<div class="col-sm-9">
									<input type="text" name="cardId" class="form-control" id="cardId" placeholder="身份证号（必填）">
								</div>
							</div>
							<!-- 姓名 -->
							<div class="form-group">
								<label for="username" class="col-sm-3 control-label">姓名</label>
								<div class="col-sm-9">
									<input type="text" name="username" class="form-control" id="username" placeholder="姓名（必填）">
								</div>
							</div>
							<!-- 新密码 -->
							<div class="form-group">
								<label for="newPassword1" class="col-sm-3 control-label">新密码</label>
								<div class="col-sm-9">
									<input type="password" name="newPassword1" class="form-control" id="newPassword1" placeholder="新密码（必填）">
								</div>
							</div>
							<!-- 重复密码 -->
							<div class="form-group">
								<label for="newPassword2" class="col-sm-3 control-label">重复密码</label>
								<div class="col-sm-9">
									<input type="password" name="newPassword2" class="form-control" id="newPassword2" placeholder="再次输入新密码（必填）">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-4">
									<button type="submit" class="btn btn-success btn-lg btn-block">找回密码</button>
								</div>
								<div class="col-sm-4">
									<a href="${pageContext.request.contextPath}/welcome.do" class="btn btn-default btn-lg btn-block">返回</a>
								</div>
							</div>
						</form>
					</div>
					<div class="col-md-4"></div>
				</div>
			</div>
		</div>
		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>