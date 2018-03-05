<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>欢迎光临</title>
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<body style="background-color:#5bc0de;">
		<div id="all" style="position: absolute;">
			<div><h1 class="text-center " style="font-size:66px;">OA管理系统</h1></div>
			<br/>
			<div class="text-center"><a href="${pageContext.request.contextPath}/welcome.do" class="btn btn-success" style="font-size:35px;">点击进入</a></div>
			<div class="text-center">
				<p>作者：软件15001班&nbsp;&nbsp;陈锡鑫</p>
				<p>时间：2017年9-12月</p>
			</div>
		</div>
		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
		<script>
		$(document).ready(function(){
			change();
		});
		$(window).resize(function(){
			change();
		});
		function change(){
			var h = ($(window).height()-$('#all').height())/2;
			var w = ($(window).width()-$('#all').width())/2;
			$('#all').css("top",h+"px");
			$('#all').css("left",w+"px");
		}
	</script>
	</body>

</html>