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
		<title>修改密码</title>
		<!-- user/changePassword.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath}/jspFile/js/md5.js"></script>
		<script type="text/javascript">
			//验证方法
			function checkFromCP(){
				var oldPassword = document.getElementById("oldPassword").value;
				if(!oldPassword){
					alert("请填写原密码！");
					event.preventDefault();
					return;
				}
				var password1 = document.getElementById("newPassword").value;
				if(!password1){
					alert("请填写新密码！");
					event.preventDefault();
					return;
				}
				var password2 = document.getElementById("newPassword2").value;
				if(!password2){
					alert("请重复输入新密码！");
					event.preventDefault();
					return;
				}
				if(password2!=password1){
					alert("两次新密码输入不同，请重新输入！");
					event.preventDefault();
					return;
				}
				document.getElementById("oldPassword").value = md5Password(oldPassword);
				document.getElementById("newPassword").value = md5Password(password1);
				document.getElementById("newPassword2").value= md5Password(password2);
			}
	
			//加载完成
			window.addEventListener("load",function(){
				//表单提交验证
				var myFormLogin = document.getElementById("myFormCP");
				if(myFormLogin.addEventListener){
					myFormLogin.addEventListener("submit",function(){
						checkFromCP();
					});
				}else{
					myFormLogin.attachEvent("onsubmit",function(){
						checkFromCP();
					});
				}
			});
		</script>
	
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>修改密码</span>
				</div>
				<div class="panel-body">
					<h3 align="center">用户密码修改</h3>
					<br />
					<div class="row">
						<div class="col-md-offset-3 col-md-6">
							<!--表单开始-->
							<form id="myFormCP" action="${pageContext.request.contextPath}/changeMyPasswordForm.do" method="post" class="form-horizontal">
								<!--流程名称-->
								<div class="form-group">
									<label for="oldPassword" class="col-sm-4 control-label">旧秘密</label>
									<div class="col-sm-8">
										<input type="password" class="form-control" name="oldPassword" id="oldPassword" placeholder="旧秘密" />
									</div>
								</div>
								<div class="form-group">
									<label for="newPassword" class="col-sm-4 control-label">新秘密</label>
									<div class="col-sm-8">
										<input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="新秘密" />
									</div>
								</div>
								<div class="form-group">
									<label for="newPassword2" class="col-sm-4 control-label">重复输入新秘密</label>
									<div class="col-sm-8">
										<input type="password" class="form-control" name="newPassword2" id="newPassword2" placeholder="重复新秘密" />
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-4">
										<input type="submit" class="form-control btn btn-success" value="提交" />
									</div>
									<div class="col-sm-4">
										<input type="reset" class="form-control btn btn-default" value="清空" />
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>