<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>欢迎</title>
		<!-- manager/addUsers.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">		
		<style type="text/css">
			#welcome_pic{
				text-align: center;
			}
		</style>
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>欢迎</span>
				</div>
				<div class="panel-body">
					<h3 align="center">欢迎来到本系统！</h3>
					<div id="welcome_pic">
						<img class="img-rounded"   src="${pageContext.request.contextPath}/jspFile/img/home_welcome.jpg"  />
					</div>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>