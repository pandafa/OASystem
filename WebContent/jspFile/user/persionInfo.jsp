<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 
	//从服务器中出来的东西
	//列表
	String tname = "张三";
	String tsex = "男";
	String tjobId = "485132";
	String tcardId = "21111111111";
	String tpart = "部门一";
	String tgroup = "小组6";
	String ttel = "192456";
	String temail = "123@ss.com";
	String taddr = "辽宁省";
--%>
<%--
	//假造数据

	request.setAttribute("piName", tname);
	request.setAttribute("piSex", tsex);
	request.setAttribute("piJobId", tjobId);
	request.setAttribute("piCardId", tcardId);
	request.setAttribute("piPart", tpart);
	request.setAttribute("piGroup", tgroup);
	request.setAttribute("piTel", ttel);
	request.setAttribute("piEmail", temail);
	request.setAttribute("piAddr", taddr);
--%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>个人信息</title>
		<!-- user/persionInfo.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>查看个人信息</span>
				</div>
				<div class="panel-body">
					<h3 align="center">个人信息</h3>
					<br />
					<form action="${pageContext.request.contextPath}/changePersonInfoForm.do" method="post" class="form-horizontal">
						<table class="table">
							<tr>
								<td>姓名</td>
								<td colspan="2"><input type="text" class="form-control" value="${piName  }" readonly /></td>
								<td>性别</td>
								<td colspan="2"><input type="text" class="form-control" value="${piSex  }" readonly /></td>
							</tr>
							<tr>
								<td>工号</td>
								<td colspan="2"><input type="text" class="form-control" value="${piJobId  }" readonly /></td>
								<td>身份证号</td>
								<td colspan="2"><input type="text" class="form-control" value="${piCardId  }" readonly /></td>
							</tr>
							<tr>
								<td>部门</td>
								<td colspan="2"><input type="text" class="form-control" value="${piPart  }" readonly /></td>
								<td>分组</td>
								<td colspan="2"><input type="text" class="form-control" value="${piGroup  }" readonly /></td>
							</tr>
							<tr>
								<td>电话</td>
								<td colspan="2"><input type="tel" name="tel" class="form-control" value="${piTel  }" /></td>
								<td>邮箱</td>
								<td colspan="2"><input type="email" name="email" class="form-control" value="${piEmail  }" /></td>
							</tr>
							<tr>
								<td>地址</td>
								<td colspan="5"><input type="text" name="addr" class="form-control" value="${piAddr  }" /></td>
							</tr>
							<tr>
								<td>职务</td>
								<td colspan="2"><input type="text" class="form-control" value="${piPost  }" readonly /></td>
							</tr>
						</table>
						<div align="center">
							<input type="submit" value="修改" class="btn btn-success" />
							<input type="reset" value="重置" class="btn btn-default" />
						</div>
					</form>
				</div>
				<div class="panel-footer"></div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>