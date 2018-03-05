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
	List<Map<String,String>> tpartMsgList = null;
	List<Map<String,String>> tcompanyMsgList = null;
	List<Map<String,String>> tnoticeMsgList = null;
--%>
<%--
	//假造数据
	
	//部门消息
	tpartMsgList = new ArrayList<Map<String,String>>();
	for(int i=0;i<3;i++){
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", "123450"+i);
		map.put("title", "我是部门标题标题标题"+i);
		map.put("date", "2017年10月"+i+"日19:09:14");
		if(i<2){
			map.put("isRead", "0");
		}else{
			map.put("isRead", "1");
		}
		tpartMsgList.add(map);
	}
	//公司消息
	tcompanyMsgList = new ArrayList<Map<String,String>>();
	for(int i=0;i<5;i++){
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", "123450"+i);
		map.put("title", "我是公司标题标题标题"+i);
		map.put("date", "2017年10月"+i+"日19:09:14");
		if(i<2){
			map.put("isRead", "0");
		}else{
			map.put("isRead", "1");
		}
		tcompanyMsgList.add(map);
	}
	//公告消息
	tnoticeMsgList = new ArrayList<Map<String,String>>();
	for(int i=0;i<6;i++){
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", "123450"+i);
		map.put("title", "我是公告标题标题标题"+i);
		map.put("date", "2017年10月"+i+"日19:09:14");
		if(i<2){
			map.put("isRead", "0");
		}else{
			map.put("isRead", "1");
		}
		tnoticeMsgList.add(map);
	}

	
	request.setAttribute("mpCompanyMsgList", tcompanyMsgList);
	request.setAttribute("mpPartMsgList", tpartMsgList);
	request.setAttribute("mpNoticeMsgList", tnoticeMsgList);
--%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>消息广场——主</title>
		<!-- message/messagePlaza.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<p>消息广场</p>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-4">
							<h3>
							<a href="${pageContext.request.contextPath}/message/noticeList.do?kind=c">公司公告</a>
						</h3>
							<div class="list-group">
								<c:forEach items="${mpCompanyMsgList }" var="map">
									<a href="${pageContext.request.contextPath}/message/lookMessage.do?msgId=${map.get('id') }" class="list-group-item">
										<p>
											<span>${map.get('title') }</span>
											<c:if test="${map.get('isRead')==0 }">
												<span class="badge" style="background-color: #d9534f">new</span>
											</c:if>
										</p>
										<div align="right">${map.get('date') }</div>
									</a>
								</c:forEach>
							</div>
						</div>
						<div class="col-md-4">
							<h3>
							<a href="${pageContext.request.contextPath}/message/noticeList.do?kind=p">部门公告</a>
						</h3>
							<div class="list-group">
								<c:forEach items="${mpPartMsgList }" var="map">
									<a href="${pageContext.request.contextPath}/message/lookMessage.do?msgId=${map.get('id') }" class="list-group-item">
										<p>
											<span>${map.get('title') }</span>
											<c:if test="${map.get('isRead')==0 }">
												<span class="badge" style="background-color: #d9534f">new</span>
											</c:if>
										</p>
										<div align="right">${map.get('date') }</div>
									</a>
								</c:forEach>
							</div>
						</div>
						<div class="col-md-4">
							<h3>
							<a href="${pageContext.request.contextPath}/message/noticeList.do?kind=g">小组公告</a>
						</h3>
							<div class="list-group">
								<c:forEach items="${mpNoticeMsgList }" var="map">
									<a href="${pageContext.request.contextPath}/message/lookMessage.do?msgId=${map.get('id') }" class="list-group-item">
										<p>
											<span>${map.get('title') }</span>
											<c:if test="${map.get('isRead')==0 }">
												<span class="badge" style="background-color: #d9534f">new</span>
											</c:if>
										</p>
										<div align="right">${map.get('date') }</div>
									</a>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>