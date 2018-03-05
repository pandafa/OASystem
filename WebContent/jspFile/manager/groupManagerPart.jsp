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
		<title>群组管理——部门</title>
		<!-- manager/groupManagerPart.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
		<script type="text/javascript">
			function liveModel(index){
				var modelName = document.getElementById("modelPartName");
				var modelId = document.getElementById("modelPartId");
				var name = document.getElementById("partName"+index).value;
				var id = document.getElementById("partId"+index).value;
				modelName.innerText = name;
				modelId.value = id;
				$('#myDelPartModel').modal('show');
			}
		</script>
	
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>群组管理——部门</span>
					<button type="button" class="btn btn-success pull-right" data-toggle="modal" data-target="#myAddPartModel">添加部门</button>
					<div class="clearfix"></div>
				</div>
				<div class="panel-body">
					<h3 align="center">管理部门</h3>
					<table class="table table-hover">
						<tr>
							<th>序号</th>
							<th>部门名称</th>
							<th>分组数</th>
							<th>成员数</th>
							<th>创建时间</th>
							<th>创建者</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${gmpManagePartList }" var="map" varStatus="status">
							<tr>
								<td>${status.index+1+(currentPage-1)*10 }</td>
								<td>${map.get('name') }</td>
								<td>${map.get('groupNum') }</td>
								<td>${map.get('memberNum') }</td>
								<td>${map.get('createDate') }</td>
								<td>${map.get('createPerson') }</td>
								<td>
									<a href="#" data-toggle="modal" onclick="liveModel('${status.index+1+(currentPage-1)*10 }')">
										<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
									</a>
									<input type="hidden" id="partName${status.index+1+(currentPage-1)*10 }" value="${map.get('name') }" />
									<input type="hidden" id="partId${status.index+1+(currentPage-1)*10 }" value="${map.get('partId') }" />
								</td>
							</tr>
						</c:forEach>
					</table>
					<div align="center">
						<nav aria-label="Page navigation">
							<ul class="pagination">
								<li class="${currentPage==1 ? 'disabled':'' }">
									<c:if test="${currentPage!=1 }">
										<a href="${pageContext.request.contextPath}/manage/groupManagerPart.do?page=${currentPage-1 }" aria-label="Previous">
											<span aria-hidden="true">&laquo;</span>
										</a>
									</c:if>
									<c:if test="${currentPage==1 }">
										<a aria-label="Previous">
											<span aria-hidden="true">&laquo;</span>
										</a>
									</c:if>
								</li>
								<c:forEach var="pageNumber" begin="1" end="${allPage }">
									<li class="${pageNumber==currentPage ? 'active':'' }">
										<a href="${pageContext.request.contextPath}/manage/groupManagerPart.do?page=${pageNumber }">${pageNumber } </a>
									</li>
								</c:forEach>
								<li class="${currentPage==allPage ? 'disabled':'' }">
									<c:if test="${currentPage!=allPage }">
										<a href="${pageContext.request.contextPath}/manage/groupManagerPart.do?page=${currentPage+1 }" aria-label="Previous">
											<span aria-hidden="true">&raquo;</span>
										</a>
									</c:if>
									<c:if test="${currentPage==allPage }">
										<a aria-label="Previous">
											<span aria-hidden="true">&raquo;</span>
										</a>
									</c:if>
								</li>
							</ul>
						</nav>
					</div>
				</div>
			</div>
		</div>
		
		<!-- del model -->
		<div id="myDelPartModel" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog modal-sm" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title">删除确认</h4>
		      </div>
		      <form action="${pageContext.request.contextPath}/manage/delPartForm.do" method="post">
		      <div class="modal-body">
		        <p>是否删除部门名称为“<span id="modelPartName"></span>”的部门？</p>
		        <input type="hidden" name="partId" id="modelPartId" />
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		        <button type="submit" class="btn btn-primary">删除</button>
		      </div>
		      </form>
		    </div>
		  </div>
		</div>
		
		<!-- add model -->
		<div id="myAddPartModel" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog modal-sm" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title">添加部门</h4>
		      </div>
		      <form action="${pageContext.request.contextPath}/manage/addPartForm.do" method="post">
		      <div class="modal-body">
		        <div class="form-group">
				    <label for="newPartName">部门名称</label>
				    <input type="text" class="form-control" name="newPartName" id="newPartName" placeholder="部门名称">
				  </div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		        <button type="submit" class="btn btn-primary">添加</button>
		      </div>
		      </form>
		    </div>
		  </div>
		</div>

		<script src="${pageContext.request.contextPath}/jspFile/js/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/jspFile/bootstrap/js/bootstrap.min.js"></script>
	</body>

</html>