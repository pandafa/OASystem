<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>登录</title>
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath}/jspFile/js/md5.js"></script>
		<script type="text/javascript">
			//验证方法
			function checkFromLogin(){
	            var username = document.getElementById("username").value;
	            if(!username){
	                alert("请填写登录名！");
	                event.preventDefault();
	                return;
	            }
	            var password = document.getElementById("password").value;
	            if(!password){
	                alert("请填写密码！");
	                event.preventDefault();
	                return;
	            }
	            document.getElementById("password").value = md5Password(password);
	        }
			
			//加载完成
			window.addEventListener("load",function(){
				//表单提交验证
				var myFormLogin = document.getElementById("myFormLogin");
				if(myFormLogin.addEventListener){
			        myFormLogin.addEventListener("submit",function(){
			        	checkFromLogin();
			        });
			    }else{
			    	myFormLogin.attachEvent("onsubmit",function(){
			    		checkFromLogin();
			        });
			    }
				
				var code = document.getElementById("code");
				code.onfocus=function(){
					if(document.getElementById('hasCode').value=='0'){
						document.getElementById('hasCode').value='1';
						//获取验证码
						getLoginCode();
					}
					
				}
				
				var codeImg = document.getElementById("codeImage");
				codeImg.onclick=function(){
					//更改验证码
					getLoginCode();
				}
				
				
			});
			
			//获取验证码
			function getLoginCode(){
				var toChange = document.getElementById('codeImage');
				toChange.setAttribute('src','${pageContext.request.contextPath}/img/loginCode/wait.jpg');
				//异步开始
				var xmlhttp=new XMLHttpRequest();
			    if (window.XMLHttpRequest){
			        xmlhttp=new XMLHttpRequest();
			    }else{
			        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			    }
			    xmlhttp.onreadystatechange=function(){
			        if(xmlhttp.readyState==4 && xmlhttp.status==200){
			            var returnStr = xmlhttp.responseText;
			            toChange.setAttribute('src','${pageContext.request.contextPath}/'+returnStr);
			        }
				}
		    	xmlhttp.open("POST","${pageContext.request.contextPath}/ajax/getLoginCodeAjax.do",true);
		    	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		    	xmlhttp.send();
	
			}
			
			//TODO 做记住密码
		</script>
	</head>

	<body class="bg-info">
		<div class="container-fluid">
		<a target="blank" href="${pageContext.request.contextPath}/jspFile/helpFile.html">使用帮助、账号及密码等</a>
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<div class="row">
						<form class="form-horizontal" id="myFormLogin"  action="${pageContext.request.contextPath}/loginForm.do" method="post">
							<h1 class="center-block" align="center">OA管理系统登录</h1>
							<br /> <br /> <br />
							<div class="form-group">
								<label for="username" class="col-sm-2 control-label">工号</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" name="username" id="username" placeholder="工号/身份证">
								</div>
							</div>
							<div class="form-group">
								<label for="password" class="col-sm-2 control-label">密码</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" name="password" id="password" placeholder="密码">
								</div>
							</div>
							<div class="form-group">
								<label for="password" class="col-sm-2 control-label">验证码</label>
								<div class="col-sm-6" title="加减法计算 ">
									<input type="hidden" id="hasCode" value="0" disabled="disabled" />
									<input type="text" class="form-control" name="code" id="code" placeholder="请输入验证码的答案">
								</div>
								<div class="col-sm-4">
									<img class="img-rounded img-thumbnail" id="codeImage" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<div class="checkbox">
										<label class="pull-left"> <input type="checkbox">记住登录名
									</label>
										<a href="${pageContext.request.contextPath}/forgetPassword.do" class="pull-right">忘记密码</a>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-8">
									<button type="submit" class="btn btn-primary btn-lg btn-block">登录</button>
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