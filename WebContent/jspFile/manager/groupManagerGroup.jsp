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
		<title>群组管理——小组</title>
		<!-- manager/groupManagerGroup.jsp -->
		<link href="${pageContext.request.contextPath}/jspFile/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript">
			function liveModel(index){
				var modelName = document.getElementById("modelGroupName");
				var modelId = document.getElementById("modelGroupId");
				var name = document.getElementById("groupName"+index).value;
				var id = document.getElementById("groupId"+index).value;
				modelName.innerText = name;
				modelId.value = id;
				$('#myDelGroupModel').modal('show');
			}
		</script>
	
	</head>

	<body>

		<div class="container-fluid">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<span>群组管理——小组</span>
					<button type="button" class="btn btn-success pull-right" data-toggle="modal" data-target="#myAddGroupModel">添加小组</button>
					<div class="clearfix"></div>
				</div>
				<div class="panel-body">
					<h3 align="center">管理小组</h3>
					<ul class="nav nav-tabs">
						<c:forEach items="${gmgPartList }" var="map">
							<li role="presentation" ${map.get('partId')==gmgCurrentPart ? 'class="active"':'' }>
								<a href="${pageContext.request.contextPath}/manage/groupManagerGroup.do?partId=${map.get('partId') }">${map.get('partName') }</a>
							</li>
						</c:forEach>
					</ul>
					<table class="table table-hover">
						<tr>
							<th>序号</th>
							<th>小组名称</th>
							<th>成员数</th>
							<th>创建时间</th>
							<th>创建者</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${gmgGroupList }" var="map" varStatus="status">
							<tr>
								<td>${status.index+1+(currentPage-1)*5 }</td>
								<td>${map.get('name') }</td>
								<td>${map.get('member') }</td>
								<td>${map.get('createDate') }</td>
								<td>${map.get('createPerson') }</td>
								<td>
									<a href="#" data-toggle="modal" onclick="liveModel('${status.index+1+(currentPage-1)*10 }')">
										<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
									</a>
									<input type="hidden" id="groupName${status.index+1+(currentPage-1)*10 }" value="${map.get('name') }" />
									<input type="hidden" id="groupId${status.index+1+(currentPage-1)*10 }" value="${map.get('groupId') }" />
								</td>
							</tr>
						</c:forEach>
						
					</table>
					<div align="center">
						<nav aria-label="Page navigation">
							<ul class="pagination">
							<li class="${currentPage==1 ? 'disabled':'' }">
								<c:if test="${currentPage!=1 }">
									<a href="${pageContext.request.contextPath}/manage/groupManagerGroup.do?page=${currentPage-1 }&partId=${gmgCurrentPart }" aria-label="Previous">
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
									<a href="${pageContext.request.contextPath}/manage/groupManagerGroup.do?page=${pageNumber }&partId=${gmgCurrentPart }">${pageNumber } </a>
								</li>
							</c:forEach>
							<li class="${currentPage==allPage ? 'disabled':'' }">
								<c:if test="${currentPage!=allPage }">
									<a href="${pageContext.request.contextPath}/manage/groupManagerGroup.do?page=${currentPage+1 }&partId=${gmgCurrentPart }" aria-label="Previous">
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
		<div id="myDelGroupModel" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog modal-sm" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title">删除确认</h4>
		      </div>
		      <form action="${pageContext.request.contextPath}/manage/delGroupForm.do" method="post">
		      <div class="modal-body">
		        <p>是否删除小组名称为“<span id="modelGroupName"></span>”的小组？</p>
		        <input type="hidden" name="GroupId" id="modelGroupId" />
		        <input type="hidden" name="partId" value="${gmgCurrentPart }" />
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
		<div id="myAddGroupModel" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog modal-sm" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title">添加小组</h4>
		      </div>
		      <form action="${pageContext.request.contextPath}/manage/addGroupForm.do" method="post">
		      <div class="modal-body">
		        <div class="form-group">
				    <label for="newGroupName">小组名称</label>
				    <input type="hidden" name="partId" value="${gmgCurrentPart }" />
				    <input type="text" class="form-control" name="newGroupName" id="newGroupName" placeholder="部门名称">
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